package com.bertolini.price_tracker_api.dto.product;


import java.math.BigDecimal;

public record ProductUpdateRequest(
        String name,
        BigDecimal targetPrice
) {
}
