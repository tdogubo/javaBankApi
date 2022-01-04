package com.etz.bankapi.service;

import com.etz.bankapi.domain.request.CreateUserRequest;
import com.etz.bankapi.domain.response.CreateUserResponse;
import com.etz.bankapi.model.User;
import com.etz.bankapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    protected UserRepository userTestRepository;
    @Mock
    protected PasswordEncoder testEncoder;
    @Autowired
    private UserService userServiceTest;

    private ModelMapper mapper;


    @BeforeEach
    void setUp() {
        userServiceTest = new UserService(userTestRepository, testEncoder);
        mapper = new ModelMapper();
    }

    @Test
    void canConvertEntityToDto() {
        //given
        User user = new User();
        user.setAddress("42 Main St");
        user.setDateOfBirth(LocalDate.ofEpochDay(1L));
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou@34W");

        //when
        CreateUserResponse actualConvertEntityToDtoResult = userServiceTest.convertEntityToDto(user);

        //then
        assertEquals(user.getAddress(), actualConvertEntityToDtoResult.getAddress());
        assertEquals(user.getDateOfBirth(), actualConvertEntityToDtoResult.getDateOfBirth());
        assertEquals(user.getEmail(), actualConvertEntityToDtoResult.getEmail());
        assertEquals(user.getFirstName(), actualConvertEntityToDtoResult.getFirstName());
        assertEquals(user.getId(), actualConvertEntityToDtoResult.getId());
        assertEquals(user.getLastName(), actualConvertEntityToDtoResult.getLastName());


    }


    @Test
    void canFetchUserFromDB() {
        //when
        userServiceTest.fetchUserFromDB(1L);

        //then
        verify(userTestRepository, times(1)).findById(1L);
    }

    @Test
    void canCreateNewUser() {
        //given
        CreateUserRequest request = new CreateUserRequest();
        request.setAddress("address");
        request.setEmail("email@email.com");
        request.setFirstName("firstName");
        request.setLastName("lastName");
        request.setPassword(testEncoder.encode("password@P123"));
        request.setDateOfBirth(LocalDate.of(2000, 1, 1));
        User user = this.mapper.map(request, User.class);


        //when
        userServiceTest.createNewUser(request);

        //then

        verify(userTestRepository, times(1)).save(user);
    }

    @Test
    void canRetrieveUser() {
        //given
        User user = userServiceTest.fetchUserFromDB(1L);

        //when


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