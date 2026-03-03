package com.bertolini.price_tracker_api.services.product;

import com.bertolini.price_tracker_api.dto.product.RegistryProductDTO;
import com.bertolini.price_tracker_api.dto.product.ReturnProductDTO;
import com.bertolini.price_tracker_api.dto.product.UpdateProductDTO;
import com.bertolini.price_tracker_api.exceptions.InvalidProductException;
import com.bertolini.price_tracker_api.infrastructure.scraping.XpathRegistry;
import com.bertolini.price_tracker_api.model.Product;
import com.bertolini.price_tracker_api.model.User;
import com.bertolini.price_tracker_api.repository.ProductRepository;
import com.bertolini.price_tracker_api.repository.UserRepository;
import com.bertolini.price_tracker_api.services.scraping.ScrapingService;
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
    public Product createProduct(RegistryProductDTO data, Long userId) {
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









