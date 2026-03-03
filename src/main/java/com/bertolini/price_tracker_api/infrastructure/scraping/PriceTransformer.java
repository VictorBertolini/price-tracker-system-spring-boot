package com.bertolini.price_tracker_api.infrastructure.scraping;


import java.math.BigDecimal;

public class PriceTransformer {
    public BigDecimal toBigDecimal(String price) {
        if (price == null)
            throw new RuntimeException("Price is not found");

        // Delete 'R$' 'US$'
        String normalized = price.replaceAll("[^0-9.,]", "").trim();

        char c;
        // Treat commas and dots in price to XXXXX.XX
        if (normalized.length() > 2) {
            c = normalized.charAt(normalized.length() - 3);
            if (c == '.') {
                 normalized = normalized.replaceAll(",", "");
            }
            else if (c == ',') {
                normalized = normalized.replaceAll("\\.", "").replaceAll(",", ".");
            }
        }

        return new BigDecimal(normalized);
    }
}
