package com.example.demo.security;

import com.example.demo.SampleUser;
import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserDetailsServiceImplTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl();

    @Before
    public void setup() {
        TestUtils.injectObject(userDetailsService, "userRepository", userRepository);
        User sampleUser = SampleUser.getSampleUser();
        when(userRepository.findByUsername(sampleUser.getUsername())).thenReturn(sampleUser);
    }

    @Test
    public void loadUserByUsername() {

        UserDetails userDetails = userDetailsService.loadUserByUsername(SampleUser.getSampleUser().getUsername());
        User user = SampleUser.getSampleUser();
        assertEquals(user.getUsername(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
    }
}