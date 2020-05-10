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

    /**
     * if you need paging and sorting, in custom methods, and
     * call w/ PageRequest(page, size, Sort)
     *
     * Optional<List<Product>> findByCategory(String category, Pageable pageable);
     */

    /**
     * JPQL queries, if you need
     * - works on JavaObjects,
     * - suitable for basic CRUD statements
     * - not suitable for DDL statements
     *
     * @Query("select p from Product p where id=:id")
     * List<Product> retrieveAllProducts(@Param("id" int id));
     */

    /**
     * Native (SQL) queries, if you need
     * - works on Db Tables,
     * - suitable for complex CRUD statements
     * - suitable for DDL statements
     *
     * @Query(value = "select * from Product p where id=:id", nativeQuery=true)
     * List<Product> retrieveAllProducts(@Param("id" int id));
     *
     */

//    This class has some default methods already implemented such as save(), findAll()
}
