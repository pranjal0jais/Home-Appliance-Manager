package com.pranjal.repository;

import com.pranjal.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductId(String productId);

    void deleteByProductId(String productId);

    @Query("SELECT p FROM Product p WHERE p.user.id = :userId")
    List<Product> findProductByUserId(@Param("userId") Long userId);

}
