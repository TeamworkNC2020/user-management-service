package com.moviesandchill.usermanagementservice.repository;

import com.moviesandchill.usermanagementservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
}
