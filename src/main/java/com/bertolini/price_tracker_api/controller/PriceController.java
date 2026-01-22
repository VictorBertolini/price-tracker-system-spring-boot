package com.bertolini.price_tracker_api.controller;

import com.bertolini.price_tracker_api.DTO.price.RegistryPriceDTO;
import com.bertolini.price_tracker_api.DTO.price.ReturnPriceDTO;
import com.bertolini.price_tracker_api.Model.entity.Price;
import com.bertolini.price_tracker_api.services.crud.PriceService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/user/{userId}/product/{productId}/price")
public class PriceController {

    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping
    public ResponseEntity<Page<ReturnPriceDTO>> getProductPrices(@PathVariable Long productId, @PageableDefault(size=10, sort={"scrapingDate"}) Pageable pageable) {
        Page<ReturnPriceDTO> page = priceService.getPrices(productId, pageable).map(ReturnPriceDTO::new);

        return ResponseEntity.ok(page);
    }

//    @PostMapping
//    public ResponseEntity registryPrice(@RequestBody @Valid RegistryPriceDTO data, @PathVariable Long productId ,UriComponentsBuilder uriBuilder) {
//        Price price = priceService.createPrice(data, productId);
//
//        URI uri = uriBuilder.path("/product/{productId}/price/{id}").buildAndExpand(productId, price.getId()).toUri();
//
//        return ResponseEntity.created(uri).body(new ReturnPriceDTO(price));
//    }
}
