package com.bertolini.price_tracker_api.dto.price;

import com.bertolini.price_tracker_api.domain.Price;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PriceResponse(
        @NotNull
        Long id,
        @NotNull
        BigDecimal price,
        @NotNull
        LocalDateTime scraping_data
) {
    public PriceResponse(Price price) {
        this(price.getId(), price.getPrice(), price.getScrapingDate());
    }
}
