package com.bertolini.price_tracker_api.controller;

import com.bertolini.price_tracker_api.dto.user.UserCreateRequest;
import com.bertolini.price_tracker_api.dto.user.UserResponse;
import com.bertolini.price_tracker_api.dto.user.UserUpdateRequest;
import com.bertolini.price_tracker_api.domain.User;
import com.bertolini.price_tracker_api.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> registryUser(@RequestBody @Valid UserCreateRequest data, UriComponentsBuilder uriBuilder) {
        User user = userService.createUser(data);

        URI uri = uriBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(new UserResponse(user));
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getUsers(@PageableDefault(size=5, sort = {"name"}) Pageable pageable) {
        Page<UserResponse> userPage = userService.getUsers(pageable).map(UserResponse::new);
        return ResponseEntity.ok(userPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> detail(@PathVariable Long id) {
        User user = userService.getUser(id);
        return ResponseEntity.ok(new UserResponse(user));
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@RequestBody @Valid UserUpdateRequest data) {
        User user = userService.updateUser(data);
        return ResponseEntity.ok(new UserResponse(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
