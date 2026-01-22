package com.bertolini.price_tracker_api.services.crud;

import com.bertolini.price_tracker_api.DTO.product.RegistryProductDTO;
import com.bertolini.price_tracker_api.DTO.product.ReturnProductDTO;
import com.bertolini.price_tracker_api.DTO.product.UpdateProductDTO;
import com.bertolini.price_tracker_api.Model.entity.Product;
import com.bertolini.price_tracker_api.Model.entity.User;
import com.bertolini.price_tracker_api.repository.ProductRepository;
import com.bertolini.price_tracker_api.repository.UserRepository;
import com.bertolini.price_tracker_api.services.scraping.ScrapingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final ScrapingService scrapingService;

    public ProductService(ProductRepository productRepository, UserRepository userRepository, ScrapingService scrapingService) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.scrapingService = scrapingService;
    }

    @Transactional
    public Product createProduct(RegistryProductDTO data, Long userId) {
        User user = userRepository.getReferenceById(userId);
        Product product = new Product(data, user);
        productRepository.save(product);

        return product;
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public Product updateProduct(UpdateProductDTO data, Long id) {
        Product product = productRepository.getReferenceById(id);
        product.updateInformation(data);

        return product;
    }

    public Page<ReturnProductDTO> getUserProducts(Long userId, Pageable pageable) {
        Page<ReturnProductDTO> products = productRepository.findByUser_id(userId, pageable).map(u -> new ReturnProductDTO(u));
        return products;
    }
}









