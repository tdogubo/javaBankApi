package com.etz.bank_api.service;

import com.etz.bank_api.config.Response;
import com.etz.bank_api.domain.request.UserRequest;
import com.etz.bank_api.domain.response.UserResponse;
import com.etz.bank_api.model.UserModel;
import com.etz.bank_api.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
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
    private UserModel dtoToEntity(UserRequest userRequest) {
        UserModel userModel = new UserModel();
        userModel.setFirstName(userRequest.getFirstName());
        userModel.setLastName(userRequest.getLastName());
        userModel.setEmail(userRequest.getEmail());
        userModel.setPassword(userRequest.getPassword());
        userModel.setDateOfBirth(userRequest.getDateOfBirth());
        return userModel;
    }
    public UserModel fetchUserFromDB(Long id){
        Optional<UserModel> isUser = userRepository.findById(id);
        return isUser.orElse(null);
    }

    public ResponseEntity<Response<UserResponse>> addNewUser(UserRequest userRequest){
        if(userRequest.getAge() < 18){
            return new ResponseEntity<>(new Response<>(false,"Age below 18"), HttpStatus.BAD_REQUEST);


        }
        UserModel newUser =  dtoToEntity(userRequest);
        UserResponse userResponse = convertEntityToDto(userRepository.save(newUser));
        return new ResponseEntity<>(new Response<>(true, userResponse),HttpStatus.CREATED);
    }

    public ResponseEntity<Response<UserResponse>> getUser(Long id){
        UserModel user = fetchUserFromDB(id);
        if(user == null){
            return new ResponseEntity<>(new Response<>(false,"User not found, register!"), HttpStatus.NOT_FOUND);
        }
        UserResponse userResponse = convertEntityToDto(user);
        return new ResponseEntity<>(new Response<>(true, userResponse),HttpStatus.OK);
    }

    public ResponseEntity<Response<UserResponse>> updateUser(Long id,UserRequest userRequest){
        UserModel user = fetchUserFromDB(id);
        if(user == null){
            return new ResponseEntity<>(new Response<>(false, ""), HttpStatus.NO_CONTENT);
        }
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        userRepository.save(user);
        UserResponse userResponse = convertEntityToDto(user);
        return new ResponseEntity<>(new Response<>(true,userResponse ),HttpStatus.OK);
}
}