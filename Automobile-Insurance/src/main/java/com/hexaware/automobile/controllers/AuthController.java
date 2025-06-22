/*
 * File: AuthController.java
 * Author: Amerthen
 * Date: 2025-06-02
 * Description: Handles user and officer login requests, authenticates credentials,
 *              and returns JWT token upon successful login.
 */
package com.hexaware.automobile.controllers;

import com.hexaware.automobile.dtos.LoginRequestDTO;
import com.hexaware.automobile.dtos.LoginResponseDTO;
import com.hexaware.automobile.util.JwtUtil;
import com.hexaware.automobile.services.CustomUserDetails;
import com.hexaware.automobile.services.impl.CustomUserDetailsService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO loginRequest) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
                )
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
            // Extract role from authorities (e.g., "ROLE_USER")
            String role = userDetails.getAuthorities().stream()
                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                    .filter(auth -> auth.startsWith("ROLE_"))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No role found for user: " + loginRequest.getEmail()));
            
            Long userId = ((CustomUserDetails) userDetails).getUserId();
            String token = jwtUtil.generateToken(userDetails.getUsername(), role, userId);

            return ResponseEntity.ok(new LoginResponseDTO(token, "Login successful"));
        } catch (BadCredentialsException e) {
            logger.warn("Invalid credentials for user: {}", loginRequest.getEmail());
            return ResponseEntity.status(401).body("Invalid email or password");
        } catch (AuthenticationException e) {
            logger.error("Authentication failed: {}", e.getMessage());
            return ResponseEntity.status(500).body("Authentication failed");
        }
    }
}