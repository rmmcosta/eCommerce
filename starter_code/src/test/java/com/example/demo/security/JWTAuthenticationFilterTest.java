package com.example.demo.security;

import com.example.demo.SampleUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Ignore
public class JWTAuthenticationFilterTest {

    private final HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
    private final HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    private final AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    @Before
    public void setup() throws IOException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String userJson = ow.writeValueAsString(SampleUser.getSampleUser());
        ServletInputStream servletInputStream = new ServletInputStream() {
            private final byte[] msg = userJson.getBytes(StandardCharsets.UTF_8);
            private int index = 0;

            @Override
            public int read() throws IOException {
                if (index >= msg.length) {
                    return -1;
                }
                return msg[index++];
            }

            @Override
            public boolean isFinished() {
                return index >= msg.length;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        };
        when(httpServletRequest.getInputStream()).thenReturn(servletInputStream);
        jwtAuthenticationFilter = new JWTAuthenticationFilter(authenticationManager);
    }

    @Test
    public void attemptAuthentication() {
        jwtAuthenticationFilter.attemptAuthentication(httpServletRequest, httpServletResponse);
    }

    @Test
    public void successfulAuthentication() throws ServletException, IOException {
        FilterChain filterChain = mock(FilterChain.class);
        Authentication authentication = mock(Authentication.class);
        com.example.demo.model.persistence.User user = SampleUser.getSampleUser();
        UserDetails userDetails = new User(user.getUsername(), user.getPassword(), Collections.emptyList());
        when(authentication.getPrincipal()).thenReturn(userDetails);
        jwtAuthenticationFilter.successfulAuthentication(httpServletRequest, httpServletResponse, filterChain, authentication);
    }
}