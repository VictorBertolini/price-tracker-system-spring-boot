package com.bertolini.price_tracker_api.Model.scraping;

import org.jsoup.nodes.Element;

import java.math.BigDecimal;

public class PriceTransformer {
    public BigDecimal toBigDecimal(Element element) {
        if (element == null)
            throw new RuntimeException("Price is not found");

        String rawText = element.text();
        String normalized = rawText.replace("R$", "")
                .replace(" ", "")
                .replace(".", "")
                .replace(",", ".");

        return new BigDecimal(normalized);
    }
}
