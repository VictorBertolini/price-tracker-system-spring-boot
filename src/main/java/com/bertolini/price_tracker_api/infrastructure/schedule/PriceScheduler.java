package com.bertolini.price_tracker_api.infrastructure.schedule;

import com.bertolini.price_tracker_api.domain.Product;
import com.bertolini.price_tracker_api.repository.ProductRepository;
import com.bertolini.price_tracker_api.service.scraping.ScrapingService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@Component
public class PriceScheduler {
    private ProductRepository productRepository;
    private ScrapingService scrapingService;

    @Scheduled(cron = "0 0 8 * * *")
    public void scheduledScraping() throws IOException {
        List<Product> products = productRepository.findAll();
        try {
            scrapingService.scrapPrice(products);
        } catch(Exception e) {
            System.err.println("Error in scheduled scraping: " + e.getMessage());
        }
    }
}
