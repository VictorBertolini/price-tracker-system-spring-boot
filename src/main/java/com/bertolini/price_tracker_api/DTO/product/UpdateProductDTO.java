package com.bertolini.price_tracker_api.DTO.product;


import java.math.BigDecimal;

public record UpdateProductDTO(
        String name,
        BigDecimal targetPrice
) {
}
