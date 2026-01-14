package com.bertolini.price_tracker_api.controller;

import com.bertolini.price_tracker_api.DTO.product.RegistryProductDTO;
import com.bertolini.price_tracker_api.DTO.product.ReturnProductDTO;
import com.bertolini.price_tracker_api.DTO.product.UpdateProductDTO;
import com.bertolini.price_tracker_api.Model.Product;
import com.bertolini.price_tracker_api.Model.User;
import com.bertolini.price_tracker_api.repository.ProductRepository;
import com.bertolini.price_tracker_api.repository.UserRepository;
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
@RequestMapping("/user/{userId}/product")
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping
    @Transactional
    public ResponseEntity productResgistry(@RequestBody @Valid RegistryProductDTO data, @PathVariable Long userId, UriComponentsBuilder uriBuilder) {
        User user = userRepository.getReferenceById(userId);
        Product product = new Product(data, user);
        System.out.println("ID do produto criado em POST: " + product.getId() + "\nID do usuário: " + user.getId());

        productRepository.save(product);

        URI uri = uriBuilder.path("/product/{id}").buildAndExpand(product.getId()).toUri();

        return ResponseEntity.created(uri).body(new ReturnProductDTO(product));
    }

    @DeleteMapping("/{productId}")
    @Transactional
    public ResponseEntity deleteProduct(@PathVariable Long productId) {
        productRepository.deleteById(productId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{productId}")
    @Transactional
    public ResponseEntity updateProduct(@RequestBody UpdateProductDTO data, @PathVariable Long productId) {
        Product product = productRepository.getReferenceById(productId);
        product.updateInformation(data);

        return ResponseEntity.ok(new ReturnProductDTO(product));
    }

    @GetMapping
    public ResponseEntity<Page<ReturnProductDTO>> getUserProducts(@PathVariable Long userId, @PageableDefault(size=5, sort={"createdAt"}) Pageable pageable) {
        Page<ReturnProductDTO> products =
                productRepository
                .findByUser_id(userId, pageable)
                .map(u -> new ReturnProductDTO(u));

        return ResponseEntity.ok(products);
    }

}
