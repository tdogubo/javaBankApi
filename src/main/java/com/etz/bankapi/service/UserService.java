package com.etz.bankapi.service;

import com.etz.bankapi.domain.request.CreateUserRequest;
import com.etz.bankapi.domain.response.AppResponse;
import com.etz.bankapi.domain.response.UserResponse;
import com.etz.bankapi.model.UserModel;
import com.etz.bankapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private UserResponse convertEntityToDto(UserModel userModel) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(userModel.getId());
        userResponse.setFirstName(userModel.getFirstName());
        userResponse.setLastName(userModel.getLastName());
        userResponse.setEmail(userModel.getEmail());
        userResponse.setDateOfBirth(userModel.getDateOfBirth());
        return userResponse;
    }

    private UserModel dtoToEntity(CreateUserRequest createUserRequest) {
        UserModel userModel = new UserModel();
        userModel.setFirstName(createUserRequest.getFirstName());
        userModel.setLastName(createUserRequest.getLastName());
        userModel.setEmail(createUserRequest.getEmail());
        userModel.setPassword(createUserRequest.getPassword());
        userModel.setDateOfBirth(createUserRequest.getDateOfBirth());
        return userModel;
    }

    public UserModel fetchUserFromDB(Long id) {
        Optional<UserModel> isUser = userRepository.findById(id);
        return isUser.orElse(null);
    }

    public ResponseEntity<AppResponse<UserResponse>> addNewUser(CreateUserRequest createUserRequest) {
        if (createUserRequest.getAge() < 18) {
            return new ResponseEntity<>(new AppResponse<>(false, "Age below 18"), HttpStatus.BAD_REQUEST);


        }
        UserModel newUser = dtoToEntity(createUserRequest);
        UserResponse userResponse = convertEntityToDto(userRepository.save(newUser));
        return new ResponseEntity<>(new AppResponse<>(true, userResponse), HttpStatus.CREATED);
    }

    public ResponseEntity<AppResponse<UserResponse>> getUser(Long id) {
        UserModel user = fetchUserFromDB(id);
        if (user == null) {
            return new ResponseEntity<>(new AppResponse<>(false, "User not found, register!"), HttpStatus.NOT_FOUND);
        }
        UserResponse userResponse = convertEntityToDto(user);
        return new ResponseEntity<>(new AppResponse<>(true, userResponse), HttpStatus.OK);
    }

    public ResponseEntity<AppResponse<UserResponse>> updateUser(Long id, CreateUserRequest createUserRequest) {
        UserModel user = fetchUserFromDB(id);
        if (user == null) {
            return new ResponseEntity<>(new AppResponse<>(false, ""), HttpStatus.NO_CONTENT);
        }
        user.setPassword(createUserRequest.getPassword());
        user.setEmail(createUserRequest.getEmail());
        userRepository.save(user);
        UserResponse userResponse = convertEntityToDto(user);
        return new ResponseEntity<>(new AppResponse<>(true, userResponse), HttpStatus.OK);
    }
}