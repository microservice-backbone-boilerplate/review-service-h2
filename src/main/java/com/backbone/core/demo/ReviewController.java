package com.backbone.core.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class ReviewController {

    @Autowired
    ReviewRepository repository;


//  Read ops

    /**
     * Get review by Id
     *
     * @param id Review's Id in URL
     * @return If find, returns Review, and HttpStatus.OK
     *         If not found, returns Null, and HttpStatus.NOT_FOUND
     *         If any exception occurs, returns null, and HttpStatus.EXPECTATION_FAILED
     */
    @GetMapping("/reviews/{id}")
    public ResponseEntity<Review> getReview(@PathVariable String id) {
        log.info("Get review by id: {}", id);

        try {
            Optional<Review> review = repository.findById(Integer.parseInt(id));

            if (review.isEmpty()) {
                log.warn("Get review by id {}: {}", id, "review not found");

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            log.info("Returned review: {}", review.get());

            return new ResponseEntity<>(review.get(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Get review by id {}: {}", id, e.getMessage());

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
    @GetMapping("v2/reviews/{id}")
    public EntityModel<Review> getReviewAsHATEOS(@PathVariable String id) {
        log.info("Get review by id: {}", id);

        try {
            Optional<Review> review = repository.findById(Integer.parseInt(id));

            if (review.isEmpty()) {
                log.warn("Get review by id {}: {}", id, "review not found");

                return new EntityModel<>(review.get(), null, null);
            }

            Link getLink = WebMvcLinkBuilder.linkTo(ReviewController.class)
                    .slash(review.get().getId())
                    .withSelfRel();

            Link deleteLink = WebMvcLinkBuilder.
                    linkTo(WebMvcLinkBuilder
                            .methodOn(ReviewController.class)
                            .deleteReview(String.valueOf(review.get().getId())))
                    .withRel("delete");

            log.info("Returned review: {}", review.get());

            return new EntityModel<>(review.get(), getLink, deleteLink);
        } catch (Exception e) {
            log.error("Get review by id {}: {}", id, e.getMessage());

            return new EntityModel<>(null, null, null);
        }
    }

    /**
     * Get all reviews. Just for demonstration purposes.
     *
     * @return If OK, returns List<Review>, and HttpStatus.OK
     *         If any exception occurs, returns null, and HttpStatus.EXPECTATION_FAILED
     */
    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> getReviews() {
        log.info("Get reviews");

        try {
            Optional<List<Review>> reviews = Optional.of(repository.findAll());

            // todo: if returns no value, means error

            log.info("Returned 3 of.. review: {}", reviews.get().subList(0,3));

            return new ResponseEntity<>(reviews.get(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Get reviews: {}", e.getMessage());

            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

//  Service ops

    /**
     * Get all reviews by productId.
     *
     * @return If OK, returns List<Review>, and HttpStatus.OK
     *         If any exception occurs, returns null, and HttpStatus.EXPECTATION_FAILED
     */
    @GetMapping("/reviews/product/{productId}")
    public ResponseEntity<List<Review>> getReviewsByProductId(@PathVariable String productId) {
        log.info("Get reviews by productId: {}", productId);

        try {
            Optional<List<Review>> reviews = repository.findReviewsByProductId(Integer.valueOf(productId));

            // todo: if returns no value, means error

            log.info("Returned reviews of product: {}", reviews.get());

            return new ResponseEntity<>(reviews.get(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Get reviews by productId {}: {}", productId, e.getMessage());

            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    /**
     * Get all reviews by userName.
     *
     * @return If OK, returns List<Review>, and HttpStatus.OK
     *         If any exception occurs, returns null, and HttpStatus.EXPECTATION_FAILED
     */
    @GetMapping("/reviews/user/{userName}")
    public ResponseEntity<List<Review>> getReviewsByUserName(@PathVariable String userName) {
        log.info("Get reviews by userName: {}", userName);

        try {
            Optional<List<Review>> reviews = repository.findReviewsByUserName(userName);

            // todo: if returns no value, means error

            log.info("Returned reviews of userName: {}", reviews.get());

            return new ResponseEntity<>(reviews.get(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Get reviews by userName {}: {}", userName, e.getMessage());

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
    @PostMapping("/reviews")
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        log.info("Create review: {}", review);

        if (review == null) {
            log.error("Create review: {}", "review is null");

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Review createdReview = repository.save(review);

            HttpHeaders headers= new HttpHeaders();
            headers.add("id", String.valueOf(createdReview.getId()));

            log.info("Created review: {}", createdReview);

            return new ResponseEntity<>(createdReview, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Create review: {}", e.getMessage());

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
    @PutMapping("/reviews/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable String id, @RequestBody Review review) {
        log.info("Update review id: {} : {}", id, review);

        if (review == null || id == null) {
            log.error("Update review: {}", "review or id is null");

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Review updatedReview = repository.save(review);

            log.info("Updated review: {}", updatedReview);

            return new ResponseEntity<>(updatedReview, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Update review id: {} : {}", id, e.getMessage());

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
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable String id) {
        log.info("Delete review id: {}", id);

        if (id == null) {
            log.error("Delete review: {}", "id is null");

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            repository.deleteById(Integer.valueOf(id));

            log.info("Deleted review id: {}", id);

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            log.error("Delete review id: {} : {}", id, e.getMessage());

            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }


}
