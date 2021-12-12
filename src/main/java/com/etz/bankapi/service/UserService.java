package com.etz.bankapi.service;

import com.etz.bankapi.domain.request.CreateUserRequest;
import com.etz.bankapi.domain.response.AppResponse;
import com.etz.bankapi.domain.response.CreateUserResponse;
import com.etz.bankapi.model.User;
import com.etz.bankapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private Integer getAge(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    private CreateUserResponse convertEntityToDto(User user) {
        CreateUserResponse createUserResponse = new CreateUserResponse();
        createUserResponse.setId(user.getId());
        createUserResponse.setFirstName(user.getFirstName());
        createUserResponse.setLastName(user.getLastName());
        createUserResponse.setEmail(user.getEmail());
        createUserResponse.setDateOfBirth(user.getDateOfBirth());
        createUserResponse.setAddress(user.getAddress());
        return createUserResponse;
    }

    private User dtoToEntity(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setFirstName(createUserRequest.getFirstName());
        user.setLastName(createUserRequest.getLastName());
        user.setEmail(createUserRequest.getEmail());
        user.setPassword(createUserRequest.getPassword());
        user.setDateOfBirth(createUserRequest.getDateOfBirth());
        user.setAddress(createUserRequest.getAddress());
        return user;
    }

    public User fetchUserFromDB(Long id) {
        Optional<User> isUser = userRepository.findById(id);
        return isUser.orElse(null);
    }

    public ResponseEntity<AppResponse<CreateUserResponse>> createNewUser(CreateUserRequest createUserRequest) {

        if (getAge(createUserRequest.getDateOfBirth()) < 18) {
            return new ResponseEntity<>(new AppResponse<>(false, "Age below 18"), HttpStatus.BAD_REQUEST);


        }
        createUserRequest.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        User newUser = dtoToEntity(createUserRequest);
        CreateUserResponse createUserResponse = convertEntityToDto(userRepository.save(newUser));
        return new ResponseEntity<>(new AppResponse<>(true, createUserResponse), HttpStatus.CREATED);
    }

    public ResponseEntity<AppResponse<CreateUserResponse>> getUser(Long id) {
        User user = fetchUserFromDB(id);
        if (user == null) {
            return new ResponseEntity<>(new AppResponse<>(false, "User not found, register!"), HttpStatus.NOT_FOUND);
        }
        CreateUserResponse createUserResponse = convertEntityToDto(user);
        return new ResponseEntity<>(new AppResponse<>(true, createUserResponse), HttpStatus.OK);
    }

    public ResponseEntity<AppResponse<CreateUserResponse>> updateUser(Long id, CreateUserRequest createUserRequest) {
        User user = fetchUserFromDB(id);
        if (user == null) {
            return new ResponseEntity<>(new AppResponse<>(false, ""), HttpStatus.NO_CONTENT);
        }
        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        user.setEmail(createUserRequest.getEmail());
        userRepository.save(user);
        CreateUserResponse createUserResponse = convertEntityToDto(user);
        return new ResponseEntity<>(new AppResponse<>(true, createUserResponse), HttpStatus.OK);
    }
}