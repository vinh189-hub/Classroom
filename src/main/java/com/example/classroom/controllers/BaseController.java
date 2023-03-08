package com.example.classroom.controllers;

import com.example.classroom.services.MyUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class BaseController {
    protected Long getUserId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userDetails = (MyUserDetails) authentication.getPrincipal();
        return userDetails.getId();
    }
}
