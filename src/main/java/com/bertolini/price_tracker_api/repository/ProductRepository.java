package com.bertolini.price_tracker_api.repository;

import com.bertolini.price_tracker_api.Model.entity.Product;
import com.bertolini.price_tracker_api.Model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByUser_id(Long userId, Pageable pageable);

    List<Product> findByUser_id(Long userId);

    Long user(User user);
}
