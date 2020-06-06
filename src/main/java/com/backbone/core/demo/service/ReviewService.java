package com.backbone.core.demo.service;

import com.backbone.core.demo.Review;
import com.backbone.core.demo.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ReviewService {

    //todo: If you use PWA on frontend-app, you dont need cache-complexity here !

    @Autowired
    ReviewRepository reviewRepository;

    @Cacheable(value = "review", key = "#id", unless = "#result == null")
    public Optional<Review> getReview(String id) {

        Optional<Review> review = reviewRepository.findById(Integer.parseInt(id));

        if (review.isEmpty())
            throw new NullPointerException("No record");

        return review;
    }

    @Cacheable(value = "reviews", unless = "#result == null")
    public Optional<Page<Review>> getReviews(String page, String size) {

        // todo: returning Optional instead of Page, and handling in ReviewController can be more concise?
        Page<Review> reviews = reviewRepository.findAll(PageRequest.of(Integer.parseInt(page),
                Integer.parseInt(size)));

        if (reviews.isEmpty())
            throw new NullPointerException("No record");

        return Optional.of(reviews);
    }

    @Caching(evict = { @CacheEvict(value = {"reviews"}),
                       @CacheEvict(value = {"review"}, allEntries = true)})
    public Optional<Review> saveReview(String id, Review review) {

        Review updatedReview = reviewRepository.save(review);

        return Optional.of(updatedReview);
    }

    @Caching(evict = { @CacheEvict(value = {"review"}, key = "#id"),
                       @CacheEvict(value = {"reviews"}) })
    public void deleteReview(String id) {

        reviewRepository.deleteById(Integer.valueOf(id));

    }

    //todo: handle deletions/updates for this new cache
    @Cacheable(value = "reviewsByProduct", key = "#productId", unless = "#result == null")
    public Optional<List<Review>> getReviewsByProductId(String productId) {

        Optional<List<Review>> reviews = reviewRepository.findByProductId(Integer.valueOf(productId));

        if (reviews.isEmpty())
            throw new NullPointerException("No record");

        return reviews;

    }

    //todo: handle deletions/updates for this new cache
    @Cacheable(value = "reviewsByUser", key = "#userName", unless = "#result == null")
    public Optional<List<Review>> getReviewsByUserName(String userName) {

        Optional<List<Review>> reviews = reviewRepository.findByUserName(userName);

        if (reviews.isEmpty())
            throw new NullPointerException("No record");

        return reviews;
    }

}
