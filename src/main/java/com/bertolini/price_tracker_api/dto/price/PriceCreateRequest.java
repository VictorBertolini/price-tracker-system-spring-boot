package com.bertolini.price_tracker_api.dto.price;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PriceCreateRequest(
        @NotNull
        BigDecimal price,
        @NotNull
        LocalDateTime scrapingData
) {
}
