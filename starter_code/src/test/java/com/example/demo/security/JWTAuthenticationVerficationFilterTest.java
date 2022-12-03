package com.example.demo.security;

import com.auth0.jwt.JWT;
import com.example.demo.SampleUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.security.authentication.AuthenticationManager;

import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Ignore
public class JWTAuthenticationVerficationFilterTest {
    private final HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
    private final HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    private final AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
    JWTAuthenticationVerficationFilter jwtAuthenticationVerficationFilter;
    FilterChain filterChain = mock(FilterChain.class);

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
        jwtAuthenticationVerficationFilter = new JWTAuthenticationVerficationFilter(authenticationManager);
    }

    @Test
    public void doFilterInternalNullHeader() throws ServletException, IOException {
        jwtAuthenticationVerficationFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);
    }

    @Test
    public void doFilterInternalWithHeader() throws ServletException, IOException {
        String token = JWT.create()
                .withSubject(SampleUser.USERNAME)
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(HMAC512(SecurityConstants.SECRET.getBytes()));
        when(httpServletRequest.getHeader(SecurityConstants.HEADER_STRING)).thenReturn("Bearer " + token);
        jwtAuthenticationVerficationFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);
    }
}