package com.bertolini.price_tracker_api.infrastructure.scraping;

import com.bertolini.price_tracker_api.domain.ShopType;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class XpathRegistry {

    private HashMap<ShopType, String> XPATH = new HashMap<>();

    public XpathRegistry() {
        XPATH.put(ShopType.KALUNGA, "//*[@id=\"precovista\"]");
        XPATH.put(ShopType.MERCADO_LIVRE, "/html/body/main/div[2]/div[6]/div[2]/div[1]/div/div[2]/div[2]/div[2]/div/div[1]/div[1]/span[1]/span/span[2]");
    }

    public String getXpath(ShopType type, String customXpath) {
        if (type == ShopType.OTHER) {
            return customXpath;
        }
        return XPATH.get(type);
    }
}
