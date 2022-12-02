package com.example.demo.controllers;

import com.example.demo.model.persistence.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.example.demo.SampleUser.USERNAME;
import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerWithRestTemplateTest {
    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void findByUserName() {
        String url = format("http://localhost:%d/api/user/%s", port, USERNAME);
        ResponseEntity<User> userResponseEntity = testRestTemplate.getForEntity(url, User.class);
        assertNotNull(userResponseEntity);
        assertEquals(HttpStatus.NOT_FOUND, userResponseEntity.getStatusCode());
    }
}
