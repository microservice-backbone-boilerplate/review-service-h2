package com.backbone.core.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Optional<Review> findById(int id);
    Optional<List<Review>> findByProductId(Integer productId);
    Optional<List<Review>> findByUserName(String userName);


//    for custom queries,if you need
//    @Query("select p from Product p")
//    List<Product> retrieveAllProducts();

//    This class has some default methods already implemented such as save(), findAll()
}
