package com.example.booking_service.service;

import com.example.booking_service.entity.Role;
import com.example.booking_service.entity.User;

public interface UserService {

    User findUserById(Long userId);

    User createUser(User user, Role role);

    User updateUser(Long userId, User user);

    void deleteUserById(Long userId);

    User findByUsername(String username);

    User findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
