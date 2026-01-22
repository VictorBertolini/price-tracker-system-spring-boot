package com.bertolini.price_tracker_api.controller;

import com.bertolini.price_tracker_api.DTO.user.RegistryUserDTO;
import com.bertolini.price_tracker_api.DTO.user.ReturnUserDTO;
import com.bertolini.price_tracker_api.DTO.user.UpdateUserDTO;
import com.bertolini.price_tracker_api.Model.entity.User;
import com.bertolini.price_tracker_api.services.crud.UserService;
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
    public ResponseEntity<ReturnUserDTO> registryUser(@RequestBody @Valid RegistryUserDTO data, UriComponentsBuilder uriBuilder) {
        User user = userService.createUser(data);

        URI uri = uriBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(new ReturnUserDTO(user));
    }

    @GetMapping
    public ResponseEntity<Page<ReturnUserDTO>> getUsers(@PageableDefault(size=5, sort = {"name"}) Pageable pageable) {
        Page<ReturnUserDTO> userPage = userService.getUsers(pageable).map(ReturnUserDTO::new);
        return ResponseEntity.ok(userPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReturnUserDTO> detail(@PathVariable Long id) {
        User user = userService.getUser(id);
        return ResponseEntity.ok(new ReturnUserDTO(user));
    }

    @PutMapping
    public ResponseEntity<ReturnUserDTO> updateUser(@RequestBody @Valid UpdateUserDTO data) {
        User user = userService.updateUser(data);
        return ResponseEntity.ok(new ReturnUserDTO(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReturnUserDTO> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
