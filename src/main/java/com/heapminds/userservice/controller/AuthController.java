package com.heapminds.userservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heapminds.userservice.dto.LoginDto;
import com.heapminds.userservice.dto.RegisterDto;
import com.heapminds.userservice.models.Roles;
import com.heapminds.userservice.models.UserEntity;
import com.heapminds.userservice.repository.RoleRepository;
import com.heapminds.userservice.repository.UserRepository;
import com.heapminds.userservice.security.JwtTokenGenerator;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenGenerator jwtTokenGenerator;
    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
            System.out.println(loginDto.toString()+" ____EWFE");

    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
    
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String token = jwtTokenGenerator.generateToken(authentication);
    
    Map<String, String> authInfo= new HashMap();
    authInfo.put("token", token);
    authInfo.put("role", authentication.getAuthorities().stream().toList().get(0).getAuthority());
        return new ResponseEntity(authInfo,HttpStatus.OK);
    }
    @PostMapping("register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDto registerDto) {
        try{log.info(registerDto.toString());
        if(userRepository.existsByUsername(registerDto.getUsername())){
            return new ResponseEntity("User already registered",HttpStatus.BAD_REQUEST);
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registerDto.getUsername());
        userEntity.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        List<Roles> roles= roleRepository.findAll();
        userEntity.setRoles(roles);
        userRepository.save(userEntity);
        return new ResponseEntity("User registered",HttpStatus.CREATED);}
        catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    }
    

