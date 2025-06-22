/*
 * UserServiceImpl.java
 * 
 * Implements UserService to manage user-related operations including:
 * - User registration with validation and password encryption (BCrypt).
 * - User login with password verification and JWT token generation.
 * - Retrieval of user details by ID and listing all users.
 * - Update user profile (excluding password and email).
 * - User deletion by ID.
 * - DTO to Entity and Entity to DTO conversion with role mapping.
 * - Logging for major operations (registration, login, update, deletion).
 * - Exception handling for validation errors, duplicate email, resource not found, and authentication failures.
 * - Validation using Jakarta Bean Validation API.
 * - Password masking in returned UserDTO for security.
 * 
 * Dependencies:
 * - Spring Security PasswordEncoder for hashing passwords.
 * - JwtUtil for JWT token creation.
 * - UserRepository for data access.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */
package com.hexaware.automobile.services.impl;

import com.hexaware.automobile.dtos.UserDTO;
import com.hexaware.automobile.dtos.LoginResponseDTO;
import com.hexaware.automobile.entities.User;
import com.hexaware.automobile.exceptions.ResourceNotFoundException;
import com.hexaware.automobile.repositories.UserRepository;
import com.hexaware.automobile.services.UserService;
import com.hexaware.automobile.util.JwtUtil;

import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    private Validator validator;

    public UserServiceImpl() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Override
    public UserDTO registerUser(@Valid UserDTO userDTO) throws Exception {
        logger.info("Registering user with email: {}", userDTO.getEmail());

        var violations = validator.validate(userDTO);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            violations.forEach(v -> sb.append(v.getMessage()).append("; "));
            throw new IllegalArgumentException("Validation failed: " + sb.toString());
        }

        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new Exception("Email already registered");
        }

        User user = dtoToEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        logger.info("User registered successfully: {}", savedUser.getEmail());

        UserDTO savedUserDTO = entityToDto(savedUser);
        savedUserDTO.setJwtToken(jwtUtil.generateToken(savedUser.getEmail(), savedUser.getRole().name(), savedUser.getId()));
        return savedUserDTO;
    }

    @Override
    public LoginResponseDTO loginUser(String email, String password) throws Exception {
        logger.info("User login attempt: {}", email);

        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            throw new Exception("Invalid email or password");
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new Exception("Invalid email or password");
        }

        String jwtToken = jwtUtil.generateToken(email, user.getRole().name(), user.getId());

        logger.info("User logged in successfully: {}", email);

        return new LoginResponseDTO(jwtToken, "Login successful");
    }

    @Override
    public UserDTO getUserById(Long id) throws Exception {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            throw new Exception("User not found");
        }
        return entityToDto(userOpt.get());
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void logoutUser(String token) {
        logger.info("Logout requested for token: {}", token);
    }
    
    @Override
    public Optional<UserDTO> updateUser(Long id, UserDTO userDTO) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setName(userDTO.getName());
            existingUser.setAddress(userDTO.getAddress());
            existingUser.setAadhaarNumber(userDTO.getAadhaarNumber());
            existingUser.setPanNumber(userDTO.getPanNumber());
            existingUser.setDob(userDTO.getDob());
            return entityToDto(userRepository.save(existingUser));
        });
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    private User dtoToEntity(UserDTO dto) {
        User user = new User();
        if (dto.getId() != null) {
            user.setId(dto.getId());
        }
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword()); 
        user.setAddress(dto.getAddress());
        user.setAadhaarNumber(dto.getAadhaarNumber());
        user.setPanNumber(dto.getPanNumber());
        user.setDob(dto.getDob());

        if (dto.getRole() != null) {
            try {
                user.setRole(User.Role.valueOf(dto.getRole().name()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid role value: " + dto.getRole());
            }
        } else {
            user.setRole(User.Role.ROLE_USER); 
        }

        return user;
    }

    private UserDTO entityToDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setAddress(user.getAddress());
        dto.setAadhaarNumber(user.getAadhaarNumber());
        dto.setPanNumber(user.getPanNumber());
        dto.setDob(user.getDob());
        dto.setPassword("********");

        if (user.getRole() != null) {
            try {
                dto.setRole(UserDTO.Role.valueOf(user.getRole().name()));
            } catch (IllegalArgumentException e) {
                dto.setRole(null);
            }
        }

        return dto;
    }
}