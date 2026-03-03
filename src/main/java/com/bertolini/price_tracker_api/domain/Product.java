package com.bertolini.price_tracker_api.model;

import com.bertolini.price_tracker_api.dto.product.RegistryProductDTO;
import com.bertolini.price_tracker_api.dto.product.UpdateProductDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "product_tb")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "target_price")
    private BigDecimal targetPrice;

    @Column(name = "xpath")
    private String xpath;

    @Enumerated(EnumType.STRING)
    @Column(name = "shop_type")
    private ShopType shopType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Price> historyPrices;

    public Product(RegistryProductDTO data, User user) {
        this.name = data.name();
        this.url = data.url();
        this.targetPrice = data.targetPrice();
        this.createdAt = LocalDateTime.now();
        this.user = user;
        this.xpath = data.xpath();
        this.shopType = data.shopType();
    }

    public void updateInformation(UpdateProductDTO data) {
        if (data.name() != null) {
            this.name = data.name();
        }

        if (data.targetPrice() != null) {
            this.targetPrice = data.targetPrice();
        }
    }
}
