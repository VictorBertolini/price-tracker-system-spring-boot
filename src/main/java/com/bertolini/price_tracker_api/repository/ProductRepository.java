package com.bertolini.price_tracker_api.repository;

import com.bertolini.price_tracker_api.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByUser_id( Long userId, Pageable pageable);
}
