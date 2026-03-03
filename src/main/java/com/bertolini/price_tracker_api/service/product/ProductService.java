package com.bertolini.price_tracker_api.service.product;

import com.bertolini.price_tracker_api.dto.product.ProductCreateRequest;
import com.bertolini.price_tracker_api.dto.product.ProductResponse;
import com.bertolini.price_tracker_api.dto.product.ProductUpdateRequest;
import com.bertolini.price_tracker_api.exception.InvalidProductException;
import com.bertolini.price_tracker_api.infrastructure.scraping.XpathRegistry;
import com.bertolini.price_tracker_api.domain.Product;
import com.bertolini.price_tracker_api.domain.User;
import com.bertolini.price_tracker_api.repository.ProductRepository;
import com.bertolini.price_tracker_api.repository.UserRepository;
import com.bertolini.price_tracker_api.service.scraping.ScrapingService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final ScrapingService scrapingService;

    private final XpathRegistry xpathRegistry;

    @Transactional
    public Product createProduct(ProductCreateRequest data, Long userId) {
        User user = userRepository.getReferenceById(userId);
        Product product = new Product(data, user);
        String xpath = xpathRegistry.getXpath(product.getShopType(), product.getXpath());
        product.setXpath(xpath);

        Product savedProduct = productRepository.save(product);
        try {
            scrapingService.scrapPrice(savedProduct);
        }
        catch (Exception e) {
            deleteProduct(savedProduct.getId());
            throw new InvalidProductException("Product couldn't be created, an error occured! " + e.getMessage());
        }
        return savedProduct;
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public Product updateProduct(ProductUpdateRequest data, Long id) {
        Product product = productRepository.getReferenceById(id);
        product.updateInformation(data);

        return product;
    }

    public Page<ProductResponse> getUserProducts(Long userId, Pageable pageable) {
        Page<ProductResponse> products = productRepository.findByUser_id(userId, pageable).map(u -> new ProductResponse(u));
        return products;
    }
}









