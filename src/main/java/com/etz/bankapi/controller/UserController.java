package com.etz.bankapi.controller;

import com.etz.bankapi.domain.request.CreateUserRequest;
import com.etz.bankapi.domain.response.AppResponse;
import com.etz.bankapi.domain.response.UserResponse;
import com.etz.bankapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {
    public final UserService userService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<AppResponse<UserResponse>> newUser(@PathVariable("id") Long userId) {
        return userService.getUser(userId);
    }

    @PostMapping
    public ResponseEntity<AppResponse<UserResponse>> registerNewUser(@Valid @RequestBody CreateUserRequest user) {
        return userService.addNewUser(user);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<AppResponse<UserResponse>> editUserInformation(@PathVariable("id") Long userId, @Valid @RequestBody CreateUserRequest user) {
        return userService.updateUser(userId, user);
    }

}
