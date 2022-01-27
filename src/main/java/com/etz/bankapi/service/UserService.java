package com.etz.bankapi.service;

import com.etz.bankapi.config.Encoder;
import com.etz.bankapi.config.Mapper;
import com.etz.bankapi.domain.request.CreateUserRequest;
import com.etz.bankapi.domain.response.AppResponse;
import com.etz.bankapi.domain.response.CreateUserResponse;
import com.etz.bankapi.model.Banker;
import com.etz.bankapi.model.Client;
import com.etz.bankapi.model.User;
import com.etz.bankapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final Encoder encoder;
    private final Mapper mapper;

    public User fetchUserFromDB(Long id) {
        Optional<User> isUser = userRepository.findById(id);
        return isUser.orElse(null);
    }

    public ResponseEntity<AppResponse<CreateUserResponse>> createNewUser(CreateUserRequest createUserRequest) {

        if (createUserRequest.getDateOfBirth().getYear() < 18) {
            return new ResponseEntity<>(new AppResponse<>(false, "Age below 18"), HttpStatus.BAD_REQUEST);


        } else {
//            createUserRequest.setPassword(encoder.passwordEncoder().encode(createUserRequest.getPassword()));
            User newUser = createUserRequest.getType().equalsIgnoreCase("banker") ?
                    mapper.modelMapper().map(createUserRequest, Banker.class) :
                    mapper.modelMapper().map(createUserRequest, Client.class);
            newUser.setPassword(encoder.passwordEncoder().encode(createUserRequest.getPassword()));
            userRepository.save(newUser);
            CreateUserResponse createUserResponse = mapper.modelMapper().map(newUser, CreateUserResponse.class);
            return new ResponseEntity<>(new AppResponse<>(true, createUserResponse), HttpStatus.CREATED);
        }
    }

    public ResponseEntity<AppResponse<CreateUserResponse>> retrieveUser(Long id) {
        User user = fetchUserFromDB(id);
        if (user == null) {
            return new ResponseEntity<>(new AppResponse<>(false, "User not found, register!"), HttpStatus.NOT_FOUND);
        }
        CreateUserResponse createUserResponse = mapper.modelMapper().map(user, CreateUserResponse.class);
        return new ResponseEntity<>(new AppResponse<>(true, createUserResponse), HttpStatus.OK);
    }

    public ResponseEntity<AppResponse<CreateUserResponse>> updateUser(Long id, CreateUserRequest createUserRequest) {
        User user = fetchUserFromDB(id);
        if (user == null) {
            return new ResponseEntity<>(new AppResponse<>(false, ""), HttpStatus.NO_CONTENT);
        }
        user.setPassword(encoder.passwordEncoder().encode(createUserRequest.getPassword()));
        user.setEmail(createUserRequest.getEmail());
        userRepository.save(user);
        CreateUserResponse createUserResponse = mapper.modelMapper().map(user, CreateUserResponse.class);
        return new ResponseEntity<>(new AppResponse<>(true, createUserResponse), HttpStatus.OK);
    }
}