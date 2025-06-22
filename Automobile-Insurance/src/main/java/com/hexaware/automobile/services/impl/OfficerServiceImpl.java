/*
 * OfficerServiceImpl.java
 * 
 * Service implementation for Officer-related business logic including:
 * - Registering officers with encrypted passwords and JWT token generation
 * - Retrieving officers by ID or all officers as DTOs
 * - Updating officer details including password changes
 * - Deleting officers by ID
 * - Authenticating officers on login with password verification and JWT token issuance
 * - Checking existence of officer email
 * 
 * Uses OfficerRepository for database operations, PasswordEncoder for security,
 * JwtUtil for JWT token creation, and maps entities to OfficerDTO to avoid exposing sensitive data.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */
package com.hexaware.automobile.services.impl;

import com.hexaware.automobile.dtos.OfficerDTO;
import com.hexaware.automobile.entities.Officer;
import com.hexaware.automobile.entities.User;
import com.hexaware.automobile.exceptions.ResourceNotFoundException;
import com.hexaware.automobile.repositories.OfficerRepository;
import com.hexaware.automobile.services.OfficerService;
import com.hexaware.automobile.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfficerServiceImpl implements OfficerService {

    @Autowired
    private OfficerRepository officerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public OfficerDTO registerOfficer(OfficerDTO dto) {
        if (officerRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        Officer officer = new Officer();
        officer.setId(dto.getOfficerId());
        officer.setName(dto.getName());
        officer.setEmail(dto.getEmail());
        officer.setPassword(passwordEncoder.encode(dto.getPassword()));
        officer.setRole(User.Role.ROLE_OFFICER);

        Officer saved = officerRepository.save(officer);

        dto.setPassword(null); // avoid sending hashed password back
        dto.setRole(saved.getRole().name());
        dto.setOfficerId(saved.getId());
        dto.setJwtToken(jwtUtil.generateToken(saved.getEmail(), saved.getRole().name(), saved.getId()));
        return dto;
    }

    @Override
    public OfficerDTO getOfficerById(Long officerId) {
        Officer officer = officerRepository.findById(officerId)
                .orElseThrow(() -> new ResourceNotFoundException("Officer not found with ID: " + officerId));
        return mapToDTO(officer);
    }

    @Override
    public List<OfficerDTO> getAllOfficers() {
        return officerRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OfficerDTO updateOfficer(Long officerId, OfficerDTO dto) {
        Officer officer = officerRepository.findById(officerId)
                .orElseThrow(() -> new ResourceNotFoundException("Officer not found with ID: " + officerId));

        officer.setName(dto.getName());
        officer.setEmail(dto.getEmail());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            officer.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        Officer updated = officerRepository.save(officer);
        return mapToDTO(updated);
    }

    @Override
    public void deleteOfficer(Long officerId) {
        Officer officer = officerRepository.findById(officerId)
                .orElseThrow(() -> new ResourceNotFoundException("Officer not found with ID: " + officerId));
        officerRepository.delete(officer);
    }

    @Override
    public OfficerDTO loginOfficer(String email, String password) {
        Officer officer = officerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid email or password"));

        if (!passwordEncoder.matches(password, officer.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        OfficerDTO dto = mapToDTO(officer);
        dto.setJwtToken(jwtUtil.generateToken(email, officer.getRole().name(), officer.getId()));
        return dto;
    }

    @Override
    public boolean existsByEmail(String email) {
        return officerRepository.existsByEmail(email);
    }

    private OfficerDTO mapToDTO(Officer officer) {
        OfficerDTO dto = new OfficerDTO();
        dto.setOfficerId(officer.getId());
        dto.setName(officer.getName());
        dto.setEmail(officer.getEmail());
        dto.setRole(officer.getRole().name());
        dto.setPassword(null); 
        return dto;
    }
}