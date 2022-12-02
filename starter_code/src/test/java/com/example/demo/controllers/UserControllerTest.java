package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static com.example.demo.SampleUser.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    UserController userController;

    BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);

    private final UserRepository userRepository = mock(UserRepository.class);

    private final CartRepository cartRepository = mock(CartRepository.class);

    @Before
    public void setup() {
        userController = new UserController();
        TestUtils.injectObject(userController, "bCryptPasswordEncoder", bCryptPasswordEncoder);
        TestUtils.injectObject(userController, "userRepository", userRepository);
        TestUtils.injectObject(userController, "cartRepository", cartRepository);
        when(bCryptPasswordEncoder.encode(PASSWORD)).thenReturn(HASHED_PASSWORD);
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(getSampleUser()));
        when(userRepository.findByUsername(USERNAME)).thenReturn(getSampleUser());
    }

    @Test
    public void findById() {
        ResponseEntity<User> userResponseEntity = userController.findById(USER_ID);
        assertNotNull(userResponseEntity);
        assertEquals(HttpStatus.OK, userResponseEntity.getStatusCode());
        User user = userResponseEntity.getBody();
        assertNotNull(user);
        assertEquals(USERNAME, user.getUsername());
    }

    @Test
    public void findByUserName() {
        ResponseEntity<User> userResponseEntity = userController.findByUserName(USERNAME);
        assertNotNull(userResponseEntity);
        assertEquals(HttpStatus.OK, userResponseEntity.getStatusCode());
        User user = userResponseEntity.getBody();
        assertNotNull(user);
        assertEquals(USER_ID, user.getId());
    }

    @Test
    public void createUser() {
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setUsername(USERNAME);
        userRequest.setPassword(PASSWORD);
        userRequest.setConfirmPassword(PASSWORD);
        ResponseEntity<User> userResponseEntity = userController.createUser(userRequest);
        assertNotNull(userResponseEntity);
        assertEquals(HttpStatus.CREATED, userResponseEntity.getStatusCode());
        User user = userResponseEntity.getBody();
        assertNotNull(user);
        assertEquals(userRequest.getUsername(), user.getUsername());
        assertEquals(HASHED_PASSWORD, user.getPassword());
    }

    @Test(expected = UserBadRequestException.class)
    public void createUserWithNoConfirmPassword() {
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setUsername(USERNAME);
        userRequest.setPassword(PASSWORD);
        userController.createUser(userRequest);
    }

    @Test(expected = UserBadRequestException.class)
    public void createUserWithoutPassword() {
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setUsername(USERNAME);
        userController.createUser(userRequest);
    }

    @Test(expected = UserBadRequestException.class)
    public void createUserWithPasswordNotEqualToConfirmPassword() {
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setUsername(USERNAME);
        userRequest.setPassword(PASSWORD);
        userRequest.setConfirmPassword("xpto");
        userController.createUser(userRequest);
    }

    @Test(expected = UserBadRequestException.class)
    public void createUserWithShortPassword() {
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setUsername(USERNAME);
        userRequest.setPassword("1234");
        userRequest.setConfirmPassword("1234");
        userController.createUser(userRequest);
    }
}
