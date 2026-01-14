package com.bertolini.price_tracker_api.DTO.price;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RegistryPriceDTO(
        @NotNull
        BigDecimal price,
        @NotNull
        LocalDateTime scrapingData
) {
}
