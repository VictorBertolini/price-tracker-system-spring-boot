package com.bertolini.price_tracker_api.dto.user;

import com.bertolini.price_tracker_api.domain.User;

public record UserResponse(
        Long id,
        String name,
        String email
) {
    public UserResponse(User user) {
        this(user.getId(),user.getName(), user.getEmail());
    }
}
