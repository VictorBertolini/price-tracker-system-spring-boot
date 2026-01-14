package com.bertolini.price_tracker_api.DTO.product;


import com.bertolini.price_tracker_api.Model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReturnProductDTO(
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

    public ReturnProductDTO(Product product) {
        this(product.getId(), product.getName(), product.getUrl(), product.getTargetPrice(), product.getCreatedAt());
    }

}
