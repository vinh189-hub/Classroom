package com.example.classroom.config;

import com.example.classroom.repositories.AuthRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class ApplicationConfig {

    private AuthRepository authRepository;

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> authRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Khong co thang nao het"));
    }
}
