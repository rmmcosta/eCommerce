package com.example.demo.security;

import com.example.demo.model.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        com.example.demo.model.persistence.User dbUser = userRepository.findByUsername(s);
        System.out.println("username:" + dbUser.getUsername());
        System.out.println("password:" + dbUser.getPassword());
        return new User(dbUser.getUsername(), dbUser.getPassword(), Collections.emptyList());
    }
}
