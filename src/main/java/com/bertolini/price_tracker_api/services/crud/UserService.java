package com.bertolini.price_tracker_api.services.crud;

import com.bertolini.price_tracker_api.DTO.user.RegistryUserDTO;
import com.bertolini.price_tracker_api.DTO.user.UpdateUserDTO;
import com.bertolini.price_tracker_api.Model.entity.User;
import com.bertolini.price_tracker_api.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createUser(RegistryUserDTO data) {
        User user = new User(data);
        userRepository.save(user);
        return user;
    }

    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User getUser(Long id) {
        User user = userRepository.getReferenceById(id);
        return user;
    }

    @Transactional
    public User updateUser(UpdateUserDTO data) {
        User user = userRepository.getReferenceById(data.id());
        user.updateInformation(data);
        return user;
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
