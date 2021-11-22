package com.etz.bank_api.controller;

import com.etz.bank_api.config.Response;
import com.etz.bank_api.domain.request.UserRequest;
import com.etz.bank_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {
    public final UserService userService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Response> getUser(@PathVariable("id") Long userId){
        return userService.getUser(userId);
    }
    @PostMapping
    public ResponseEntity<Response> registerNewUser(@RequestBody UserRequest user){
        return userService.addNewUser(user);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Response> editUserInformation(@PathVariable("id") Long userId , @RequestBody UserRequest user){
        return userService.updateUser(userId, user);
    }

}
