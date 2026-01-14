package com.bertolini.price_tracker_api.DTO.product;

import jakarta.validation.constraints.NotBlank;


import java.math.BigDecimal;

public record RegistryProductDTO(
        @NotBlank
        String name,
        @NotBlank
        String url,

        BigDecimal targetValue
) {
}
