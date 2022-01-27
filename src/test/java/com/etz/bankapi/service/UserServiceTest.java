package com.etz.bankapi.service;

import com.etz.bankapi.config.Encoder;
import com.etz.bankapi.config.Mapper;
import com.etz.bankapi.domain.request.CreateUserRequest;
import com.etz.bankapi.model.User;
import com.etz.bankapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {

    @Mock
    UserRepository userTestRepository;
    @Mock
    Encoder encoder;
    @Mock
    Mapper mapper;
    @InjectMocks
    UserService userServiceTest;

    @Test
    void canFetchUserFromDB() {
        //when
//        userServiceTest.fetchUserFromDB(anyLong());
//        when(userTestRepository.findById(anyLong())).thenReturn(null);
        //then
        verify(userTestRepository, times(1)).findById(anyLong());
    }

    @Test
    void canRetrieveUser() {
        //when
        userServiceTest.retrieveUser(1L);
        //then
        verify(userTestRepository, times(1)).findById(1L);
    }

    @Test
    void canNotCreateNewUser() {
        //given
        CreateUserRequest request = new CreateUserRequest();
        request.setAddress("address");
        request.setEmail("email@email.com");
        request.setFirstName("firstname");
        request.setLastName("lastname");
        request.setEmail("user@gmail.com");
        request.setPassword("password@P123");
        request.setDateOfBirth(LocalDate.now());
        request.setLga("lga");
        request.setType("banker");
        request.setPhoneNumber("08123456677");

        //when
        userServiceTest.createNewUser(request);

        //then
        assertEquals(userServiceTest.createNewUser(request).getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void canCreateNewUser() {
        //given
        String encryptedPassword = "74hghd8474jf";
//        CreateUserRequest request = new CreateUserRequest();
//        request.setAddress("address");
//        request.setEmail("email@email.com");
//        request.setFirstName("firstname");
//        request.setLastName("lastname");
//        request.setEmail("user@gmail.com");
//        request.setPassword("password@P123");
//        request.setDateOfBirth(LocalDate.parse("2000-01-01"));
//        request.setLga("lga");
//        request.setType("banker");
//        request.setPhoneNumber("08123456677");
//        UserEntity storedUserDetails - new UserEntity();
//        when(userRepository.findByEmail(anyString())). thenReturn(nul1);
//        when(utils.generateAddressId(anyInt())). thenReturn("hgfnghtyrir884");
//        when(utils.generateUserId(anyInt())). thenReturn(userId);
//        when(bCryptPasswordEncoder.encode(anyString))). thenReturn(encryptedPassword);
//        when(userRepository.save(any(UserEntity.class))). thenReturn(storedUserDetails);

        //when
//        assertThat(request.getDateOfBirth()).isBefore(LocalDate.ofYearDay(2004, 1));
        when(encoder.passwordEncoder().encode(anyString())).thenReturn(encryptedPassword);
        when(mapper.modelMapper().map(any(), User.class)).thenReturn(new User());


//        when
//        userServiceTest.createNewUser(request);

        //then
//        assertEquals(userServiceTest.createNewUser(request).getStatusCode(), HttpStatus.CREATED);
    }


    @Test
    void canUpdateUser() {
        //given
        CreateUserRequest request = new CreateUserRequest();

        //when
        userServiceTest.updateUser(1L, request);

        //then
        verify(userTestRepository, times(1)).findById(1L);
    }


}