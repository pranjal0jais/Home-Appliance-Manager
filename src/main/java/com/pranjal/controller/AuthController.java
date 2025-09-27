package com.pranjal.controller;

import com.pranjal.dtos.AuthRequest;
import com.pranjal.dtos.UserRequest;
import com.pranjal.entity.User;
import com.pranjal.jwt.Utility;
import com.pranjal.service.Implementation.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/api/v1/auth")
@RestController()
@RequiredArgsConstructor
public class AuthController {

    private final UserDetailServiceImpl userDetailServiceImpl;
    private final AuthenticationManager authenticationManager;
    private final Utility jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthRequest authRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
            com.pranjal.entity.User user =
                    (User) userDetailServiceImpl.loadUserByUsername(authRequest.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "userId", user.getUserId(),
                    "fullname", user.getFullName(),
                    "email", user.getEmail(),
                    "token", jwtUtil.generateToken(user.getUsername())
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRequest userRequest){
        boolean isSaved = userDetailServiceImpl.saveUser(userRequest);
        if(isSaved){
            return ResponseEntity.status(HttpStatus.CREATED).body("User Registered Successfully");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User Registration Failed");
    }
}
