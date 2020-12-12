package com.backbone.core.demo;

import com.backbone.core.demo.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * ReviewController
 *
 * In a normal scenario, we gonna need basic
 *
 * - GET: Read ops (by id, by page&size, some filtering),
 * - GET: Service ops (other services may need).
 * - POST: Create
 * - PUT: Update
 * - DELETE: Delete ops (by id)
 * - GET: Search ops (by category+page&size)
 *
 * The main points are, how to position,
 * - PUT (partial update) and POST (whole new creation) requests,
 * - Filtering and Search (coupled w/ db selection) concepts.
 * - Orchestration layer in service ops: Low degree of coupling vs non-coupling
 *
 */
@RestController
@Slf4j
public class ReviewController {

    private static final String PAGE = "0";
    private static final String SIZE = "10";

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ReviewService reviewService;


//  Read ops

    //todo: enhance log-message format => sometimes we may return other than object like count of records...

    /**
     * Get review by Id.
     * It caches data after 1st call w/ id.
     *
     * @cached: review
     *
     * log format: message [param1, param2] : returned-object
     *     message can be => Not found, Get, Returned, Exception
     *
     * @param id Review's Id in URL
     * @return If find, returns Review, and HttpStatus.OK
     *         If parameter is not valid (such as string instead int), returns Null, and HttpStatus.BAD_REQUEST
     *         If not found (empty Review), returns Null, and HttpStatus.NOT_FOUND
     *         If any exception occurs, returns null, and HttpStatus.EXPECTATION_FAILED
     */
    @GetMapping("/review/{id}")
    public ResponseEntity<Review> getReview(@PathVariable String id) {
        log.info("Get [id:{}]", id);

        try {
            AtomicReference<ResponseEntity<Review>> result = new AtomicReference<>();

            reviewService.getReview(id)
                         .ifPresent(review -> {

                            log.info("Returned [id:{}] : {}", id, review);

                            result.set(new ResponseEntity<>(review, HttpStatus.OK));
                         });

            return result.get();

        } catch (NumberFormatException nfe) {
            log.error("Bad request [id:{}] : {}", id, nfe.getMessage());

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NullPointerException npe) {
            log.warn("Not found [id:{}] : {}", id, npe.getMessage());

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Exception [id:{}] : {}", id, e.getMessage());

            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    /**
     * Get all reviews via paging and size.
     * It caches data after 1st call.
     *
     * @cached: review
     *
     *
     * @param page default=0 to ...N
     * @param size default=10, if empty
     * @return If OK, returns List<Review>, and HttpStatus.OK
     *         If parameter is not valid (such as string instead int), returns Null, and HttpStatus.BAD_REQUEST
     *         If page or size have no record, returns null, and HttpStatus.NO_CONTENT
     *         If any exception occurs, returns null, and HttpStatus.EXPECTATION_FAILED
     */
    @GetMapping(path = {"/reviews",
                        "/reviews/page/{page}",
                        "/reviews/page/{page}/size/{size}"})
    public ResponseEntity<List<Review>> getReviews(@PathVariable(required = false) String page,
                                                   @PathVariable(required = false) String size) {

        //set defaults
        String p = Optional.ofNullable(page).orElse(PAGE);
        String s = Optional.ofNullable(size).orElse(SIZE);

        log.info("Get [page:{}, size:{}]", p, s);

        try {
            AtomicReference<ResponseEntity<List<Review>>> result = new AtomicReference<>();

            reviewService.getReviews(p, s)
                         .ifPresent(reviews -> {

                            // todo: returning all reviews in logs vs part of it. better way (debug level) may be good
                            log.info("Returned [page:{}, size:{}] : {}", p, s, reviews.getContent());

                            result.set(new ResponseEntity<>(reviews.getContent(), HttpStatus.OK));
                         });

            return result.get();

        } catch (NumberFormatException nfe) {
            log.error("Bad request [page:{}, size:{}] : {}", p, s, nfe.getMessage());

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NullPointerException npe) {
            log.warn("No content [page:{}, size:{}] : {}", p, s, npe.getMessage());

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Exception [page:{}, size:{}] : {}", p, s, e.getMessage());

            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

//  Service ops

    /**
     * Get all reviews by productId.
     * It caches data after 1st call by product's id
     *
     * @cached: review
     *
     * @param productId product id
     * @return If OK, returns List<Review>, and HttpStatus.OK
     *         If productId has no record, returns null, and HttpStatus.NO_CONTENT
     *         If parameter is not valid (such as string instead int), returns Null, and HttpStatus.BAD_REQUEST
     *         If any exception occurs, returns null, and HttpStatus.EXPECTATION_FAILED
     */
    @GetMapping("/reviews/product/{productId}")
    public ResponseEntity<List<Review>> getReviewsByProductId(@PathVariable String productId) {

        log.info("Get [productId:{}]", productId);

        try {
            AtomicReference<ResponseEntity<List<Review>>> result = new AtomicReference<>();

            reviewService.getReviewsByProductId(productId)
                         .ifPresent(reviews -> {

                            log.info("Returned [productId:{}] : {}", productId, reviews);

                            result.set(new ResponseEntity<>(reviews, HttpStatus.OK));

                         });

            return result.get();

        } catch (NumberFormatException nfe) {
            log.error("Bad request [productId:{}] : {}", productId, nfe.getMessage());

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NullPointerException npe) {
            log.warn("No content [productId:{}] : {}", productId, npe.getMessage());

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Exception [productId:{}] : {}", productId, e.getMessage());

            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    /**
     * Get all reviews by userName.
     *
     * It caches data after 1st call by product's id
     *
     * @cached: review
     *
     * @param userName user name
     * @return If OK, returns List<Review>, and HttpStatus.OK
     *         If userName has no record, returns null, and HttpStatus.NO_CONTENT
     *         If any exception occurs, returns null, and HttpStatus.EXPECTATION_FAILED
     */
    @GetMapping("/reviews/user/{userName}")
    public ResponseEntity<List<Review>> getReviewsByUserName(@PathVariable String userName) {

        log.info("Get [userName:{}]", userName);

        try {
            AtomicReference<ResponseEntity<List<Review>>> result = new AtomicReference<>();

            reviewService.getReviewsByUserName(userName)
                         .ifPresent(reviews -> {

                            log.info("Returned [userName:{}] : {}", userName, reviews);

                            result.set(new ResponseEntity<>(reviews, HttpStatus.OK));

                         });

            return result.get();

        } catch (NullPointerException npe) {
            log.warn("Not found [userName:{}] : {}", userName, npe.getMessage());

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Exception [userName:{}] : {}", userName, e.getMessage());

            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

//  CreateUpdateDelete ops

    //todo: in elasticsearch, PUT is arranged as partial product update (and waits some part of object),
    // POST is for creating whole new one.

    /**
     * Save review. If,
     *   id == 0, creates
     *   id != 0 and isValid, updates
     *   id != 0 and notValid, creates and risks cache!
     *
     * @param review as JSON in RequestBody
     * @return If OK, returns updated or created Review, and HttpStatus.OK
     *         If RequestBody or Id not OK, returns Null, and HttpStatus.BAD_REQUEST
     *         If any exception occurs, returns null, and HttpStatus.EXPECTATION_FAILED
     */
    @PostMapping("/review")
    public ResponseEntity<Review> saveReview(@RequestBody Review review) {

        log.info("Save [review:{}]", review);

        AtomicReference<ResponseEntity<Review>> result = new AtomicReference<>();

        try {
            Optional.of(review)
                    .flatMap(review1 ->
                            reviewService.saveReview(String.valueOf(review1.getId()), review1))
                                         .ifPresent(updatedReview -> {
                                            log.info("Saved : {}", updatedReview);

                                            result.set(new ResponseEntity<>(updatedReview, HttpStatus.OK));
                                         });

            return result.get();

        } catch (IllegalArgumentException iae) {
            log.error("Bad request [review:{}]", review);

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {

            log.error("Exception [review:{}] : {}", review, e.getMessage());

            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    /**
     * Delete review.
     *
     * @param id of Review in URL
     * @return If OK, returns HttpStatus.OK
     *         If Input is not OK, returns HttpStatus.BAD_REQUEST
     *         If any exception occurs, returns HttpStatus.EXPECTATION_FAILED
     */
    @DeleteMapping("/review/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable String id) {

        log.info("Delete [id:{}]", id);

        AtomicReference<ResponseEntity<Void>> result = new AtomicReference<>();

        try {

            Optional.of(id)
                    .ifPresent(id1 -> {
                        reviewService.deleteReview(id1);

                        log.info("Deleted [id:{}]", id1);

                        result.set(new ResponseEntity<>(HttpStatus.OK));
                    });

            return result.get();

        } catch (IllegalArgumentException iae) {
            log.error("Bad request [id:{}]", id);

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            log.error("Exception [id:{}] : {}", id, e.getMessage());

            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

//  Search ops

}
