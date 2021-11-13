package com.etz.bank_api.controller;

import com.etz.bank_api.domain.UserDTO;
import com.etz.bank_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {
    public final UserService userService;

    @PostMapping
    public void registerNewUser(@RequestBody UserDTO user){
                userService.addNewUser(user);
    }
}
