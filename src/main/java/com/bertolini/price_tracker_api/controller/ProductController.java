package com.bertolini.price_tracker_api.controller;

import com.bertolini.price_tracker_api.dto.product.ProductCreateRequest;
import com.bertolini.price_tracker_api.dto.product.ProductResponse;
import com.bertolini.price_tracker_api.dto.product.ProductUpdateRequest;
import com.bertolini.price_tracker_api.domain.Product;
import com.bertolini.price_tracker_api.service.product.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/user/{userId}/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> productResgistry(@RequestBody @Valid ProductCreateRequest data, @PathVariable Long userId, UriComponentsBuilder uriBuilder) {
        Product product = productService.createProduct(data, userId);

        URI uri = uriBuilder.path("/product/{id}").buildAndExpand(product.getId()).toUri();

        return ResponseEntity.created(uri).body(new ProductResponse(product));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{productId}")
    @Transactional
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody ProductUpdateRequest data, @PathVariable Long productId) {
        Product product = productService.updateProduct(data, productId);
        return ResponseEntity.ok(new ProductResponse(product));
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getUserProducts(@PathVariable Long userId, @PageableDefault(size=5, sort={"createdAt"}) Pageable pageable) {
        Page<ProductResponse> products = productService.getUserProducts(userId, pageable);
        return ResponseEntity.ok(products);
    }

}
