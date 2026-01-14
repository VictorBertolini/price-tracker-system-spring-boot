package com.bertolini.price_tracker_api.repository;

import com.bertolini.price_tracker_api.Model.Price;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long> {

    Page<Price> findByProduct_Id(Long id, Pageable pageable);
}
