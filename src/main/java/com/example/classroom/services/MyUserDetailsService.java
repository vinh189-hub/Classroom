package com.example.classroom.services;

import com.example.classroom.entities.User;
import com.example.classroom.exceptions.UserNotFoundException;
import com.example.classroom.repositories.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AuthRepository authRepository;
    @Override
    public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return MyUserDetails.build(user);
    }

    public String getEmailByUserId(Long id) {
        var user = this.authRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user with id doesn't exist"));
        return user.getEmail();
    }
}
