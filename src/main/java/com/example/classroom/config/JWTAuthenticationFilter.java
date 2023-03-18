package com.example.classroom.config;


import com.example.classroom.entities.User;
import com.example.classroom.exceptions.ForbiddenException;
import com.example.classroom.repositories.AuthRepository;
import com.example.classroom.services.MyUserDetails;
import com.example.classroom.services.MyUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JWTService jwtService;
    @Autowired
    private final MyUserDetailsService userDetailsService;

    @Autowired
    private final AuthRepository authRepository;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userID;
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        userID = jwtService.extractUserID(jwt);

        if (userID != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var email = this.userDetailsService.getEmailByUserId((Long.parseLong(userID)));
            var userDetails = userDetailsService.loadUserByUsername(email);
            if (jwtService.isValidToken(jwt, userDetails)) {
                var authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);

    }


//    @Override
//    protected void doFilterInternal(
//            @NotNull HttpServletRequest request,
//            @NotNull HttpServletResponse response,
//            @NotNull FilterChain filterChain)
//            throws ServletException, IOException, ForbiddenException {
//
//        final String authHeader = request.getHeader("Authorization");
//        final String jwt;
//        final String userID;
//
//        var route = request.getServletPath();
//        if (route.equals("/api/v1/auth/authenticate") || route.equals("/api/v1/auth/register")){
//            filterChain.doFilter(request, response);
//            return;
//        }
//        if (authHeader == null || !authHeader.startsWith("Bearer")) {
//            var err = new ForbiddenException("Forbidden");
//            response.setHeader("error", err.getMessage());
//            response.setStatus(HttpStatus.FORBIDDEN.value());
//            Map<String, String> error = new HashMap<>();
//            error.put("message", err.getMessage());
//            OutputStream responseStream = response.getOutputStream();
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.writeValue(responseStream, error);
//            responseStream.flush();
//            filterChain.doFilter(request, response);
//            return;
//        }
//        try{
//            jwt = authHeader.substring(7);
//            userID = jwtService.extractUserID(jwt);
//            if (userID != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                var email = this.userDetailsService.getEmailByUserId((Long.parseLong(userID)));
//                var userDetails = userDetailsService.loadUserByUsername(email);
//                if (jwtService.isValidToken(jwt, userDetails)) {
//                    var authToken = new UsernamePasswordAuthenticationToken(
//                            userDetails,
//                            null,
//                            userDetails.getAuthorities()
//                    );
//                    authToken.setDetails(
//                            new WebAuthenticationDetailsSource().buildDetails(request)
//                    );
//                    SecurityContextHolder.getContext().setAuthentication(authToken);
//                }
//            }
//            filterChain.doFilter(request, response);
//        }catch (Exception err){
////            var err = new ForbiddenException("Forbidden");
//            response.setHeader("error", err.getMessage());
//            response.setStatus(HttpStatus.FORBIDDEN.value());
//            Map<String, String> error = new HashMap<>();
//            error.put("message", err.getMessage());
//            new ObjectMapper().writeValue(response.getOutputStream(), error);
//        }
//    }
}
