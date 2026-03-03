package com.bertolini.price_tracker_api.model;


import com.bertolini.price_tracker_api.dto.price.RegistryPriceDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "price_tb")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "price_id")
    private Long id;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "scraping_date")
    private LocalDateTime scrapingDate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Price(RegistryPriceDTO data, Product product) {
        this.price = data.price();
        this.scrapingDate = data.scrapingData();
        this.product = product;
    }

    public Price(BigDecimal price, Product product) {
        this.product = product;
        this.price = price;
        this.scrapingDate = LocalDateTime.now();
    }
}
