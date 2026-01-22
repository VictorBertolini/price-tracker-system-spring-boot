package com.bertolini.price_tracker_api.Model.scraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Scraper {

    public Document connect(String url) throws IOException {
        return Jsoup.connect(url)
                .data("query", "Java")
                .userAgent("Mozilla")
                .cookie("auth", "token")
                .timeout(3000)
                .get();
    }

    public Elements getElements(Document doc, String Xpath) {
        return doc.selectXpath(Xpath);
    }
}
