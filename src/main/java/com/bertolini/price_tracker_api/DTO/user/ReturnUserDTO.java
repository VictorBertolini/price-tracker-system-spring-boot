package com.bertolini.price_tracker_api.DTO.user;

import com.bertolini.price_tracker_api.Model.User;

public record ReturnUserDTO(
        Long id,
        String name,
        String email
) {
    public ReturnUserDTO(User user) {
        this(user.getId(),user.getName(), user.getEmail());
    }
}
