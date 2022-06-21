package com.example.cryptus.controller;

import com.example.cryptus.repository.JwtFakeRepo;
import com.example.cryptus.security.JwtConfig;
import com.example.cryptus.service.ApplicationUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping(path = "/status")
public class StatusController {
    private JwtFakeRepo jwtFakeRepo;
    private JwtConfig jwtConfig;
    private SecretKey secretKey;
    private ApplicationUserService applicationUserService;

    @Autowired
    public StatusController(JwtFakeRepo jwtFakeRepo, JwtConfig jwtConfig, SecretKey secretKey, ApplicationUserService applicationUserService) {
        this.jwtFakeRepo = jwtFakeRepo;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
        this.applicationUserService = applicationUserService;
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response){
        String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());
        String token = authorizationHeader.replace( jwtConfig.getTokenPrefix(), "");
        Jws<Claims> claimsJws;
        claimsJws = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        String username = body.getSubject();
        jwtFakeRepo.addJwtToRepo(username, token);
//        if(jwtFakeRepo.containsKey(username)){
//            System.out.println("Yes FakeRepo contains this token");
//        }else System.out.println("No FakeRepo does not contain this token");
//        return token;
        return ResponseEntity.ok().build();
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws java.io.IOException {
        String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());

        if (authorizationHeader !=null && authorizationHeader.startsWith(jwtConfig.getTokenPrefix())){
            try {
                String token = authorizationHeader.replace( jwtConfig.getTokenPrefix(), "");
                Jws<Claims> claimsJws;
                claimsJws = Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(token);
                Claims body = claimsJws.getBody();
                String username = body.getSubject();
                UserDetails user = applicationUserService.loadUserByUsername(username);
                String accessToken = Jwts.builder()
                        .setSubject(user.getUsername())
                        .claim("authorities", user.getAuthorities())
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 1000))
                        .signWith(secretKey)
                        .compact();
                Map<String, String>tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("token", token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }catch (Exception exception){
                response.setHeader("error", exception.getMessage());
                Map<String, String>error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }else {
            throw new RuntimeException("Token is missing");
        }
    }

}
