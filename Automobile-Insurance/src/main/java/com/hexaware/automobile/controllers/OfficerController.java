/*
 * File: OfficerController.java
 * Author: Amerthen
 * Date: 2025-06-02
 * Description: Exposes REST APIs for officer management including registration,
 *              login, retrieval, update, and deletion of officer records with
 *              appropriate logging and error handling.
 */
package com.hexaware.automobile.controllers;

import com.hexaware.automobile.dtos.OfficerDTO;
import com.hexaware.automobile.services.OfficerService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/officers")
public class OfficerController {

    private static final Logger logger = LoggerFactory.getLogger(OfficerController.class);

    @Autowired
    private OfficerService officerService;

    
    @PostMapping("/register")
    public ResponseEntity<?> registerOfficer(@Valid @RequestBody OfficerDTO officerDTO) {
        try {
            OfficerDTO registeredOfficer = officerService.registerOfficer(officerDTO);
            logger.info("Officer registered: {}", registeredOfficer.getEmail());
            return new ResponseEntity<>(registeredOfficer, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error registering officer: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    
    @PostMapping("/login")
    public ResponseEntity<?> loginOfficer(@RequestBody OfficerLoginRequest loginRequest) {
        try {
            OfficerDTO officer = officerService.loginOfficer(loginRequest.getEmail(), loginRequest.getPassword());
            logger.info("Officer logged in: {}", loginRequest.getEmail());
            return ResponseEntity.ok(officer);
        } catch (Exception e) {
            logger.error("Officer login failed: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<?> getOfficerById(@PathVariable Long id) {
        try {
            OfficerDTO officer = officerService.getOfficerById(id);
            return ResponseEntity.ok(officer);
        } catch (Exception e) {
            logger.error("Officer not found: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    
    @GetMapping
    public ResponseEntity<List<OfficerDTO>> getAllOfficers() {
        List<OfficerDTO> officers = officerService.getAllOfficers();
        return ResponseEntity.ok(officers);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOfficer(@PathVariable Long id, @Valid @RequestBody OfficerDTO officerDTO) {
        try {
            OfficerDTO updated = officerService.updateOfficer(id, officerDTO);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            logger.error("Error updating officer: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOfficer(@PathVariable Long id) {
        try {
            officerService.deleteOfficer(id);
            logger.info("Officer deleted with ID: {}", id);
            return ResponseEntity.ok("Officer deleted successfully");
        } catch (Exception e) {
            logger.error("Error deleting officer: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    
    public static class OfficerLoginRequest {
        private String email;
        private String password;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
