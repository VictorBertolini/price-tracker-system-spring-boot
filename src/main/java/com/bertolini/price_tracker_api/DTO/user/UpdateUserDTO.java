package com.bertolini.price_tracker_api.DTO.user;

import jakarta.validation.constraints.Email;

public record UpdateUserDTO(
        Long id,
        String name,
        @Email
        String email,
        String password
) {
}
