package com.heapminds.userservice.security;

import java.util.Date;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.heapminds.userservice.constants.JwtConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenGenerator {

    public String generateToken(Authentication authentication){
        String userName = authentication.getName();
        Date currentDate = new Date();
        Date anotherDate = new Date(currentDate.getTime()+  JwtConstants.ANOTHER_TIME);
        String token = Jwts.builder().setSubject(userName).setIssuedAt(currentDate).setExpiration(anotherDate)
        .signWith( SignatureAlgorithm.HS256,JwtConstants.SECRET_KEY).compact();
        return token;
    }

    public String getUsername(String token){
        Claims username = Jwts.parser().setSigningKey(JwtConstants.SECRET_KEY).parseClaimsJws(token).getBody();
        return username.getSubject();
    }

    public Boolean isValidToken(String token){
        try{
            System.out.println(token);
        Jwts.parser().setSigningKey(JwtConstants.SECRET_KEY).parseClaimsJws(token);
    return true;
    }
        catch(Exception e){
            throw new AuthenticationCredentialsNotFoundException(e.getMessage());
        }

    }
    
}
