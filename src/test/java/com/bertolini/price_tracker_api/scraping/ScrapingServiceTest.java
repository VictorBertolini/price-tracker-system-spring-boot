//package com.bertolini.price_tracker_api.scraping;
//
//import com.bertolini.price_tracker_api.infrastructure.scraping.Scraper;
//import com.bertolini.price_tracker_api.model.entity.Product;
//import com.bertolini.price_tracker_api.model.entity.ShopType;
//import com.bertolini.price_tracker_api.model.entity.User;
//import com.bertolini.price_tracker_api.repository.PriceRepository;
//import com.bertolini.price_tracker_api.services.scraping.ScrapingService;
//import lombok.AllArgsConstructor;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cglib.core.Local;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//@AllArgsConstructor
//public class ScrapingServiceTest {
//
//    @Test
//    void shouldTestIfUrlIsValid() throws IOException {
//
//        User user = new User((long) 10, "Pedro", "pedro@gmail.com", "1234", LocalDateTime.now(), null);
//
//        Product product = new Product(
//                (long) 1,
//                "Projetor",
//                "https://shopee.com.br/Projetor-4K-HD-150-Polegadas-Celular-Tv-B0x-Xbox-PS-Pc-Wifi-e-Bluetooth-HY300-Magcubic-110-220-i.526596865.21099196966?extraParams=%7B%22display_model_id%22%3A229405160193%2C%22model_selection_logic%22%3A3%7D",
//                new BigDecimal(150),
//                null,
//                ShopType.SHOPEE,
//                LocalDateTime.now(),
//                user,
//                null
//                );
//
//        scrapingService.scrapPrice(product);
//    }
//
//
//}
