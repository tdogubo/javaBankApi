package com.etz.bank_api.service;

import com.etz.bank_api.domain.UserDTO;
import com.etz.bank_api.model.UserModel;
import com.etz.bank_api.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private UserDTO convertEntityToDto(UserModel userModel) {
        UserDTO userDto = new UserDTO();
        userDto.setFirstName(userModel.getFirstName());
        userDto.setLastName(userModel.getLastName());
        return userDto;
    }
    private UserModel dtoToEntity(UserDTO userDto) {
        UserModel userModel = new UserModel();
        userModel.setFirstName(userDto.getFirstName());
        userModel.setLastName(userDto.getLastName());
        userModel.setEmail(userDto.getEmail());
        userModel.setPassword(userDto.getPassword());
        userModel.setDateOfBirth(userDto.getDateOfBirth());
        return userModel;
    }

    public void addNewUser(UserDTO userDTO){
        if(userDTO.getAge() < 18){
            throw new IllegalStateException("Oooops!! Can't open account!");
        }
         UserModel newUser =  dtoToEntity(userDTO);
        userRepository.save(newUser);
    }
}
