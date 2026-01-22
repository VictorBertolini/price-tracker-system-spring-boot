package com.bertolini.price_tracker_api.services.crud;

import com.bertolini.price_tracker_api.Model.entity.Price;
import com.bertolini.price_tracker_api.repository.PriceRepository;
import com.bertolini.price_tracker_api.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PriceService {

    private final PriceRepository priceRepository;

    private final ProductRepository productRepository;

    public PriceService(PriceRepository priceRepository, ProductRepository productRepository) {
        this.priceRepository = priceRepository;
        this.productRepository = productRepository;
    }

    public Page<Price> getPrices(Long productId, Pageable pageable) {
        return priceRepository.findByProduct_Id(productId, pageable);
    }

//    @Transactional
//    public Price createPrice(RegistryPriceDTO data, Long productId) {
//        Product product = productRepository.getReferenceById(productId);
//        Price price = new Price(data, product);
//        priceRepository.save(price);
//
//        return price;
//    }



}
