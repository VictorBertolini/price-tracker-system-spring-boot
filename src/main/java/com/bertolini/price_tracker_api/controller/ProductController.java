package com.bertolini.price_tracker_api.controller;

import com.bertolini.price_tracker_api.DTO.product.RegistryProductDTO;
import com.bertolini.price_tracker_api.DTO.product.ReturnProductDTO;
import com.bertolini.price_tracker_api.DTO.product.UpdateProductDTO;
import com.bertolini.price_tracker_api.Model.entity.Product;
import com.bertolini.price_tracker_api.services.crud.ProductService;
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
    public ResponseEntity<ReturnProductDTO> productResgistry(@RequestBody @Valid RegistryProductDTO data, @PathVariable Long userId, UriComponentsBuilder uriBuilder) {
        Product product = productService.createProduct(data, userId);

        URI uri = uriBuilder.path("/product/{id}").buildAndExpand(product.getId()).toUri();

        return ResponseEntity.created(uri).body(new ReturnProductDTO(product));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ReturnProductDTO> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{productId}")
    @Transactional
    public ResponseEntity<ReturnProductDTO> updateProduct(@RequestBody UpdateProductDTO data, @PathVariable Long productId) {
        Product product = productService.updateProduct(data, productId);
        return ResponseEntity.ok(new ReturnProductDTO(product));
    }

    @GetMapping
    public ResponseEntity<Page<ReturnProductDTO>> getUserProducts(@PathVariable Long userId, @PageableDefault(size=5, sort={"createdAt"}) Pageable pageable) {
        Page<ReturnProductDTO> products = productService.getUserProducts(userId, pageable);
        return ResponseEntity.ok(products);
    }

}
