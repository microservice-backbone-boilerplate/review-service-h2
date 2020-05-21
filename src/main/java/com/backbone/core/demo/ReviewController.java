package com.backbone.core.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@Slf4j
public class ReviewController {

    private static final String PAGE = "0";
    private static final String SIZE = "10";

    @Autowired
    ReviewRepository repository;


//  Read ops

    /**
     * Get review by Id
     *
     * log format: message [param1, param2] : returned-object
     *     message can be => Not found, Get, Returned, Exception
     *
     * @param id Review's Id in URL
     * @return If find, returns Review, and HttpStatus.OK
     *         If not found, returns Null, and HttpStatus.NOT_FOUND
     *         If any exception occurs, returns null, and HttpStatus.EXPECTATION_FAILED
     */
    @GetMapping("/review/{id}")
    public ResponseEntity<Review> getReview(@PathVariable String id) {
        log.info("Get [id:{}]", id);

        try {
            Optional<Review> review = repository.findById(Integer.parseInt(id));

            if (review.isEmpty()) {
                log.error("Not Found [id:{}]", id);

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            log.info("Returned [id:{}] : {}", id, review.get());

            return new ResponseEntity<>(review.get(), HttpStatus.OK);
        } catch (NumberFormatException nfe) {
            log.error("Bad request [id:{}] : {}", id, nfe.getMessage());

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Exception [id:{}] : {}", id, e.getMessage());

            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    /**
     * Get review by Id w/ HATEOS support (GET and DELETE links)
     *
     * @param id Reviews's Id in URL
     * @return If find, returns Review, and get link, and delete link
     *         If not found, returns Null
     *         If any exception occurs, returns null
     */
    @GetMapping("v2/review/{id}")
    public EntityModel<Review> getReviewAsHATEOS(@PathVariable String id) {
        log.info("Get [id:{}]", id);

        try {
            Optional<Review> review = repository.findById(Integer.parseInt(id));

            if (review.isEmpty()) {
                log.error("Not found [id:{}]", id);

                return new EntityModel<>(null, null, null);
            }

            Link getLink = WebMvcLinkBuilder.linkTo(ReviewController.class)
                    .slash(review.get().getId())
                    .withSelfRel();

            Link deleteLink = WebMvcLinkBuilder.
                    linkTo(WebMvcLinkBuilder
                            .methodOn(ReviewController.class)
                            .deleteReview(String.valueOf(review.get().getId())))
                    .withRel("delete");

            log.info("Returned [id:{}]: {}", id, review.get());

            return new EntityModel<>(review.get(), getLink, deleteLink);
        } catch (NumberFormatException nfe) {
            log.error("Bad request [id:{}] : {}", id, nfe.getMessage());

            return new EntityModel<>(null, null, null);
        } catch (Exception e) {
            log.error("Exception [id:{}] : {}", id, e.getMessage());

            return new EntityModel<>(null, null, null);
        }
    }

    /**
     * Get all reviews via paging and size.
     *
     * @param page default=0 to ...N
     * @param size default=10, if empty
     * @return If OK, returns List<Review>, and HttpStatus.OK
     *         If any exception occurs, returns null, and HttpStatus.EXPECTATION_FAILED
     *         If page or size has no record, returns null, and HttpStatus.NOT_FOUND
     */
    @GetMapping(path = {"/reviews",
                        "/reviews/page/{page}",
                        "/reviews/page/{page}/size/{size}"})
    public ResponseEntity<List<Review>> getReviews(@PathVariable(required = false) String page,
                                                   @PathVariable(required = false) String size) {

        // set defaults
        page = (page == null) ? PAGE : page;
        size = (size == null) ? SIZE : size;

        log.info("Get [page:{}, size:{}]", page, size);

        try {
            Page<Review> reviews = repository.findAll(PageRequest.of(Integer.parseInt(page),
                                                                     Integer.parseInt(size)));

            // if no records found,
            if (reviews.isEmpty()) {
                log.error("Not found [page:{}, size:{}]", page, size);

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            log.info("Returned [page:{}, size:{}] : {}", page,
                                                         size,
                                                         reviews.get().collect(Collectors.toList()));

            return new ResponseEntity<>(reviews.get().collect(Collectors.toList()), HttpStatus.OK);
        } catch (NumberFormatException nfe) {
            log.error("Bad request [page:{}, size:{}] : {}", page, size, nfe.getMessage());

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Exception [page:{}, size:{}] : {}", page, size, e.getMessage());

            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

//  Service ops

    /**
     * Get all reviews by productId.
     *
     * @param productId product id
     * @return If OK, returns List<Review>, and HttpStatus.OK
     *         If any exception occurs, returns null, and HttpStatus.EXPECTATION_FAILED
     *         If productId has no record, returns null, and HttpStatus.NOT_FOUND
     */
    @GetMapping("/reviews/product/{productId}")
    public ResponseEntity<List<Review>> getReviewsByProductId(@PathVariable String productId) {
        log.info("Get [productId:{}]", productId);

        try {
            Optional<List<Review>> reviews = repository.findByProductId(Integer.valueOf(productId));

            // if no records found,
            if (reviews.isEmpty()) {
                log.error("Not found [productId:{}]", productId);

                return new ResponseEntity<>(HttpStatus.OK);
            }

            log.info("Returned [productId:{}] : {}", productId, reviews.get());

            return new ResponseEntity<>(reviews.get(), HttpStatus.OK);
        } catch (NumberFormatException nfe) {
            log.error("Bad request [productId:{}] : {}", productId, nfe.getMessage());

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Exception [productId:{}] : {}", productId, e.getMessage());

            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    /**
     * Get all reviews by userName.
     *
     * @param userName user name
     * @return If OK, returns List<Review>, and HttpStatus.OK
     *         If any exception occurs, returns null, and HttpStatus.EXPECTATION_FAILED
     *         If userName has no record, returns null, and HttpStatus.NOT_FOUND
     */
    @GetMapping("/reviews/user/{userName}")
    public ResponseEntity<List<Review>> getReviewsByUserName(@PathVariable String userName) {
        log.info("Get [userName:{}]", userName);

        try {
            Optional<List<Review>> reviews = repository.findByUserName(userName);

            // if no records found,
            if (reviews.isEmpty()) {
                log.error("Not found [userName:{}]", userName);

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            log.info("Returned [userName:{}]", reviews.get());

            return new ResponseEntity<>(reviews.get(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception [userName:{}] : {}", userName, e.getMessage());

            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

//  CreateUpdateDelete ops

    /**
     * Create review.
     *
     * @param review as JSON in RequestBody
     * @return If OK, creates Review, new Id in Headers, and HttpStatus.CREATED
     *         If RequestBody not OK, returns Null, and HttpStatus.BAD_REQUEST
     *         If any exception occurs, returns null, and HttpStatus.EXPECTATION_FAILED
     */
    @PostMapping("/review")
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        log.info("Create [review:{}]", review);

        if (review == null) {
            log.error("Bad request [review:{}]", (Object) null);

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Review createdReview = repository.save(review);

            HttpHeaders headers= new HttpHeaders();
            headers.add("id", String.valueOf(createdReview.getId()));

            log.info("Created : {}", createdReview);

            return new ResponseEntity<>(createdReview, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Exception [review:{}] : {}", review, e.getMessage());

            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    /**
     * Update review.
     *
     * @param id of Review in URL
     * @param review as JSON in RequestBody
     * @return If OK, returns updated Review, and HttpStatus.OK
     *         If RequestBody or Id not OK, returns Null, and HttpStatus.BAD_REQUEST
     *         If any exception occurs, returns null, and HttpStatus.EXPECTATION_FAILED
     */
    @PutMapping("/review/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable String id, @RequestBody Review review) {
        log.info("Update [id:{}, review:{}]", id, review);

        if (review == null || id == null) {
            log.error("Bad request [id:{}, review:{}]", id, review);

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Review updatedReview = repository.save(review);

            log.info("Updated : {}", updatedReview);

            return new ResponseEntity<>(updatedReview, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception [id:{}] : {}", id, e.getMessage());

            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    /**
     * Delete review.
     *
     * @param id of Review in URL
     * @return If OK, returns HttpStatus.OK
     *         If Id not OK, returns HttpStatus.BAD_REQUEST
     *         If any exception occurs, returns HttpStatus.EXPECTATION_FAILED
     */
    @DeleteMapping("/review/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable String id) {
        log.info("Delete [id:{}]", id);

        if (id == null) {
            log.error("Bad request [id:{}]", (Object) null);

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            repository.deleteById(Integer.valueOf(id));

            log.info("Deleted [id:{}]", id);

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            log.error("Exception [id:{}] : {}", id, e.getMessage());

            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

//  Search ops

}
