package com.bertolini.price_tracker_api.services.scraping;

import com.bertolini.price_tracker_api.model.Price;
import com.bertolini.price_tracker_api.model.Product;
import com.bertolini.price_tracker_api.infrastructure.scraping.PriceTransformer;
import com.bertolini.price_tracker_api.infrastructure.scraping.Scraper;
import com.bertolini.price_tracker_api.repository.PriceRepository;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ScrapingService {

    private final Scraper scraper;

    private final PriceRepository priceRepository;

    private final PriceTransformer transformer = new PriceTransformer();

    public ScrapingService(Scraper scraper, PriceRepository priceRepository) {
        this.scraper = scraper;
        this.priceRepository = priceRepository;
    }

    public void scrapPrice(Product product) throws IOException {
        scraper.connect();
        scrap(product);
        scraper.disconnect();
    }

    public void scrapPrice(List<Product> productList) throws IOException {
        scraper.connect();
        for (Product product : productList) {
            scrap(product);
        }
        scraper.disconnect();
    }

    private void scrap(Product product) {
        String url = product.getUrl();
        String xpath = product.getXpath();
        String text_price = scraper.getElements(url, xpath);

        BigDecimal price = transformer.toBigDecimal(text_price);
        priceRepository.save(new Price(price, product));
    }
}
