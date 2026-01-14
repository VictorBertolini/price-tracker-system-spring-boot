package com.bertolini.price_tracker_api.controller;

import com.bertolini.price_tracker_api.DTO.user.RegistryUserDTO;
import com.bertolini.price_tracker_api.DTO.user.ReturnUserDTO;
import com.bertolini.price_tracker_api.DTO.user.UpdateUserDTO;
import com.bertolini.price_tracker_api.Model.User;
import com.bertolini.price_tracker_api.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity registryUser(@RequestBody @Valid RegistryUserDTO data, UriComponentsBuilder uriBuilder) {
        User user = new User(data);
        repository.save(user);

        URI uri = uriBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(new ReturnUserDTO(user));
    }

    @GetMapping
    public ResponseEntity<Page<ReturnUserDTO>> getUsers(@PageableDefault(size=5, sort = {"name"}) Pageable pageable) {
        Page<ReturnUserDTO> userPage = repository.findAll(pageable).map(u -> new ReturnUserDTO(u));

        return ResponseEntity.ok(userPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity detail(@PathVariable Long id) {
        User user = repository.getReferenceById(id);
        return ResponseEntity.ok(new ReturnUserDTO(user));
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateUser(@RequestBody @Valid UpdateUserDTO data) {
        User user = repository.getReferenceById(data.id());
        user.updateInformation(data);

        return ResponseEntity.ok(new ReturnUserDTO(user));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
