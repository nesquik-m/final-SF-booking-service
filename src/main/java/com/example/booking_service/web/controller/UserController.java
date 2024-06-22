package com.example.booking_service.web.controller;

import com.example.booking_service.entity.Role;
import com.example.booking_service.entity.RoleType;
import com.example.booking_service.entity.User;
import com.example.booking_service.exception.AlreadyExistsException;
import com.example.booking_service.mapper.UserMapper;
import com.example.booking_service.service.UserService;
import com.example.booking_service.web.model.request.UserRequest;
import com.example.booking_service.web.model.response.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    // TODO: Совершить действия с профилем пользователя может только либо сам пользователь (владелец), либо администратор.

    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest request,
                                                   @RequestParam RoleType roleType) {
        if (userService.existsByUsername(request.getUsername())) {
            throw new AlreadyExistsException(
                    MessageFormat.format("Пользователь с именем {0} уже существует!", request.getUsername()));
        }

        if (userService.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException(
                    MessageFormat.format("Пользователь с email {0} уже существует!", request.getEmail()));
        }

        User createdUser = userService.createUser(userMapper.requestToUser(request), Role.from(roleType));
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.userToUserResponse(createdUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("id") Long userId,
                                                   @RequestBody @Valid UserRequest request) {
        User updatedUser = userService.updateUser(userId, userMapper.requestToUser(request));

        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.userToUserResponse(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") Long userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

}
