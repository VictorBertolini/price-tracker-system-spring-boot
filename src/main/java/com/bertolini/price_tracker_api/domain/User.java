package com.bertolini.price_tracker_api.model;

import com.bertolini.price_tracker_api.dto.user.RegistryUserDTO;
import com.bertolini.price_tracker_api.dto.user.UpdateUserDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "user_tb")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Product> registeredProducts;

    public User(RegistryUserDTO data) {
        this.name = data.name();
        this.email = data.email();
        this.password = data.password();
        this.createdAt = LocalDateTime.now();
    }

    public void updateInformation(@Valid UpdateUserDTO data) {
        if (data.name() != null) {
            this.name = data.name();
        }
        if (data.email() != null) {
            this.email = data.email();
        }
        if (data.password() != null) {
            this.password = data.password();
        }
    }
}