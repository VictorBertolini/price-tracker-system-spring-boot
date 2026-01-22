package com.bertolini.price_tracker_api.repository;

import com.bertolini.price_tracker_api.Model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
