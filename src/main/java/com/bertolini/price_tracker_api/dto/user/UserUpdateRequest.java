package com.bertolini.price_tracker_api.dto.user;

import jakarta.validation.constraints.Email;

public record UserUpdateRequest(
        Long id,
        String name,
        @Email
        String email,
        String password
) {
}
