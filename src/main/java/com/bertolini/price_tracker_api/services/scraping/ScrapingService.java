package com.bertolini.price_tracker_api.services.scraping;

import com.bertolini.price_tracker_api.Model.entity.Price;
import com.bertolini.price_tracker_api.Model.entity.Product;
import com.bertolini.price_tracker_api.Model.scraping.PriceTransformer;
import com.bertolini.price_tracker_api.Model.scraping.Scraper;
import com.bertolini.price_tracker_api.repository.PriceRepository;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ScrapingService {

    private final Scraper scraper;

    private final PriceRepository priceRepository;

    public ScrapingService(Scraper scraper, PriceRepository priceRepository) {
        this.scraper = scraper;
        this.priceRepository = priceRepository;
    }

    public void scrapPrice(List<Product> productList) throws IOException {
        Document doc;
        PriceTransformer transformer = new PriceTransformer();
        for (Product product : productList) {
            String url = product.getUrl();
            doc = scraper.connect(url);

            Elements elements = scraper.getElements(doc, product.getXpath());

            BigDecimal price = transformer.toBigDecimal(elements.first());

            priceRepository.save(new Price(price, product));
        }
    }


}
