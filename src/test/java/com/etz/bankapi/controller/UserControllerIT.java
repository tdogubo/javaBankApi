//package com.etz.bankapi.controller;
//
//import com.etz.bankapi.BankApiApplication;
//import com.etz.bankapi.domain.request.CreateUserRequest;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.http.*;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.time.LocalDate;
//import java.util.Objects;
//
//import static org.junit.Assert.assertSame;
//import static org.junit.Assert.assertTrue;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = BankApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class UserControllerIT {
//    @LocalServerPort
//    private int port;
//
//    @Test
//    public void loginUserTest() {
//        String url = "http://localhost:" + port + "/api/v1/users/1";
//        TestRestTemplate restTemplate = new TestRestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", "application/json");
//        HttpEntity<Objects> entity = new HttpEntity<>(null, headers);
//        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
//        assertSame(response.getStatusCode(), HttpStatus.NOT_FOUND);
//        assertTrue(Objects.requireNonNull(response.getBody()).contains("User not found"));
//
//
//    }
//
//    @Test
//    public void registerUserTest() {
//        String url = "http://localhost:" + port + "/api/v1/users";
//        TestRestTemplate restTemplate = new TestRestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", "application/json");
//        CreateUserRequest user = new CreateUserRequest();
//        user.setFirstName("test");
//        user.setLastName("test");
//        user.setEmail("test@email.com");
//        user.setPassword("12@aB349okk");
//        user.setAddress("test");
//        user.setDateOfBirth(LocalDate.of(2000, 1, 1));
//        HttpEntity<CreateUserRequest> entity = new HttpEntity<>(user, headers);
//        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
//        assertSame(response.getStatusCode(), HttpStatus.CREATED);
//
//
//    }
//
//    public void setPort(int port) {
//        this.port = port;
//    }
//}
