package com.bertolini.price_tracker_api.dto.product;


import com.bertolini.price_tracker_api.domain.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(
        @NotNull
        Long id,

        @NotBlank
        String name,

        @NotBlank
        String url,

        BigDecimal targetPrice,

        @NotNull
        LocalDateTime createdAt
) {

    public ProductResponse(Product product) {
        this(product.getId(), product.getName(), product.getUrl(), product.getTargetPrice(), product.getCreatedAt());
    }

}
