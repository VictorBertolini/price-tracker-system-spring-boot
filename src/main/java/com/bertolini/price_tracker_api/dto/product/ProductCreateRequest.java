package com.bertolini.price_tracker_api.dto.product;

import com.bertolini.price_tracker_api.domain.ShopType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


import java.math.BigDecimal;

public record ProductCreateRequest(
        @NotBlank
        String name,

        @NotBlank
        String url,

        @NotNull
        ShopType shopType,

        String xpath,

        BigDecimal targetPrice
) {
}
