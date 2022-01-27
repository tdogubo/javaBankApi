package com.etz.bankapi.controller;

import com.etz.bankapi.domain.request.CreateUserRequest;
import com.etz.bankapi.domain.response.ApiResponse;
import com.etz.bankapi.domain.response.AppResponse;
import com.etz.bankapi.domain.response.CreateUserResponse;
import com.etz.bankapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    public final UserService userService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<AppResponse<CreateUserResponse>> loginUser(@PathVariable("id") Long userId) {
        return userService.retrieveUser(userId);
    }

    @PostMapping
    public ResponseEntity<AppResponse<CreateUserResponse>> registerNewUser(@Valid @RequestBody CreateUserRequest user) {
        return userService.createNewUser(user);
    }

    @GetMapping(path = "/external")
    public ResponseEntity<AppResponse<List<ApiResponse>>> callApi() {
        String url = "https://jsonplaceholder.typicode.com/todos";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<ApiResponse>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ApiResponse>>() {
        });
        List<ApiResponse> testResp = response.getBody();
        return new ResponseEntity<>(new AppResponse<>(true, testResp), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<AppResponse<CreateUserResponse>> editUserInformation(@PathVariable("id") Long userId, @Valid @RequestBody CreateUserRequest user) {
        return userService.updateUser(userId, user);
    }

}
