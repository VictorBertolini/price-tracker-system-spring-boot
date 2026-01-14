package com.bertolini.price_tracker_api.controller;

import com.bertolini.price_tracker_api.DTO.price.RegistryPriceDTO;
import com.bertolini.price_tracker_api.DTO.price.ReturnPriceDTO;
import com.bertolini.price_tracker_api.DTO.product.ReturnProductDTO;
import com.bertolini.price_tracker_api.Model.Price;
import com.bertolini.price_tracker_api.Model.Product;
import com.bertolini.price_tracker_api.repository.PriceRepository;
import com.bertolini.price_tracker_api.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/user/{userId}/product/{productId}/price")
public class PriceController {
    @Autowired
    PriceRepository priceRepository;

    @Autowired
    ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<Page<ReturnPriceDTO>> getProductPrices(@PathVariable Long productId, @PageableDefault(size=10, sort={"scrapingDate"}) Pageable pageable) {
        Page<ReturnPriceDTO> page = priceRepository.findByProduct_Id(productId, pageable).map(p -> new ReturnPriceDTO(p));

        return ResponseEntity.ok(page);
    }

    @PostMapping
    @Transactional
    public ResponseEntity registryPrice(@RequestBody @Valid RegistryPriceDTO data, @PathVariable Long productId ,UriComponentsBuilder uriBuilder) {

        Product product = productRepository.getReferenceById(productId);
        Price price = new Price(data, product);
        priceRepository.save(price);

        URI uri = uriBuilder.path("/product/{productId}/price/{id}").buildAndExpand(product.getId(), price.getId()).toUri();

        return ResponseEntity.created(uri).body(new ReturnPriceDTO(price));
    }
}
