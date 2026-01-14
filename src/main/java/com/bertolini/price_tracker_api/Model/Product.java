package com.bertolini.price_tracker_api.Model;

import com.bertolini.price_tracker_api.DTO.product.RegistryProductDTO;
import com.bertolini.price_tracker_api.DTO.product.UpdateProductDTO;
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
@Table(name = "Product_tb")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "targetPrice")
    private BigDecimal targetPrice;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Price> historyPrices;

    public Product(RegistryProductDTO data, User user) {
        this.name = data.name();
        this.url = data.url();
        this.targetPrice = data.targetValue();
        this.createdAt = LocalDateTime.now();
        this.user = user;
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
