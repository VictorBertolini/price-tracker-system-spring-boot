package com.bertolini.price_tracker_api.controller;

import com.bertolini.price_tracker_api.dto.price.PriceResponse;
import com.bertolini.price_tracker_api.service.price.PriceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/{userId}/product/{productId}/price")
public class PriceController {

    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping
    public ResponseEntity<Page<PriceResponse>> getProductPrices(@PathVariable Long productId, @PageableDefault(size=10, sort={"scrapingDate"}) Pageable pageable) {
        Page<PriceResponse> page = priceService.getPrices(productId, pageable).map(PriceResponse::new);

        return ResponseEntity.ok(page);
    }
}
