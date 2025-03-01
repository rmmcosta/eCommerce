package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.security.SecurityConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static java.lang.String.format;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @GetMapping("/id/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return ResponseEntity.of(userRepository.findById(id));
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> findByUserName(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@NotNull @RequestBody CreateUserRequest createUserRequest) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String createUserRequestJson = ow.writeValueAsString(createUserRequest);
        User user = new User();
        user.setUsername(createUserRequest.getUsername());
        if (createUserRequest.getPassword() == null) {
            logger.error(format("The password is mandatory %s", createUserRequestJson));
            throw new UserBadRequestException("The password is mandatory");
        }
        if (createUserRequest.getConfirmPassword() == null) {
            logger.error(format("The confirmation password is mandatory %s", createUserRequestJson));
            throw new UserBadRequestException("The confirmation password is mandatory");
        }
        if (!createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())) {
            logger.error(format("The password and confirmation password must be the same %s", createUserRequestJson));
            throw new UserBadRequestException("The password and confirmation password must be the same");
        }
        if (createUserRequest.getPassword().length() < SecurityConstants.PASSWORD_MIN_LENGTH) {
            logger.error(format("The password must be at least %d chars long %s", SecurityConstants.PASSWORD_MIN_LENGTH, createUserRequestJson));
            throw new UserBadRequestException(format("The password must be at least %d chars long", SecurityConstants.PASSWORD_MIN_LENGTH));
        }
        //we need to hash the password before saving it
        user.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));
        Cart cart = new Cart();
        cartRepository.save(cart);
        user.setCart(cart);
        userRepository.save(user);
        String userJson = ow.writeValueAsString(user);
        logger.info(format("User %s created with success", userJson));
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

}
