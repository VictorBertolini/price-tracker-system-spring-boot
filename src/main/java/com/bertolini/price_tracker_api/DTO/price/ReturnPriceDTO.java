package com.bertolini.price_tracker_api.DTO.price;

import com.bertolini.price_tracker_api.Model.Price;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReturnPriceDTO(
        @NotNull
        Long id,
        @NotNull
        BigDecimal price,
        @NotNull
        LocalDateTime scraping_data
) {
    public ReturnPriceDTO(Price price) {
        this(price.getId(), price.getPrice(), price.getScrapingDate());
    }
}
