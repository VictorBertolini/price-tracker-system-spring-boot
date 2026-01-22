package com.bertolini.price_tracker_api.Model.entity;

public enum ShopType {
    SHOPEE("/html/body/div[1]/div/div[2]/div/div/div[1]/div/div/div/div[2]/section/section[2]/div/div[3]/div/section/div/div[1]"),
    MERCADO_LIVRE("mer"),
    ALIEXPREX("Merc");

    private final String xpath;

    ShopType(String xpath) {
        this.xpath = xpath;
    }

    public String getXpath() {
        return xpath;
    }
}
