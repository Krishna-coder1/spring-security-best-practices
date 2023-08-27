package com.heapminds.userservice.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heapminds.userservice.security.RoleValidator;

@RestController
@RequestMapping("/test")
public class TestController {
    
    @PreAuthorize(RoleValidator.IS_PREMIUM_USER)
    @GetMapping("/hi")
    public ResponseEntity<String> test(@RequestHeader("Authorization") String authToken) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    System.out.println("User roles: " + authentication.getAuthorities());
    return new ResponseEntity("Hi buddy",HttpStatus.CREATED);
    }
    
}
