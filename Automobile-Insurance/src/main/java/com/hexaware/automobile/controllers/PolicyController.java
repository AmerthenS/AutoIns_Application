/*
 * File: PolicyController.java
 * Author: Amerthen
 * Date: 2025-06-02
 * Description: Provides role-based REST endpoints where users can view their own policies securely,
 *              and officers have permissions to view all policies and update policy details,
 *              leveraging Spring Security to enforce access and extracting user details from the
 *              security context for personalized data retrieval.
 */
package com.hexaware.automobile.controllers;

import com.hexaware.automobile.dtos.PolicyDTO;
import com.hexaware.automobile.services.CustomUserDetails;
import com.hexaware.automobile.services.PolicyService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<PolicyDTO>> getMyPolicies() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        
        Long userId = extractUserIdFromAuthentication(auth);
        
        List<PolicyDTO> policies = policyService.getPoliciesByUserId(userId);
        return ResponseEntity.ok(policies);
    }

    
    private Long extractUserIdFromAuthentication(Authentication auth) {
    
        Object principal = auth.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getUserId();
        }
        
        throw new IllegalStateException("User ID not found in authentication principal");
    }


    
    @GetMapping
    @PreAuthorize("hasRole('OFFICER')")
    public ResponseEntity<List<PolicyDTO>> getAllPolicies() {
        List<PolicyDTO> policies = policyService.getAllPolicies();
        return ResponseEntity.ok(policies);
    }

    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('OFFICER')")
    public ResponseEntity<PolicyDTO> updatePolicy(@PathVariable Long id, @Valid @RequestBody PolicyDTO policyDTO) {
        PolicyDTO updatedPolicy = policyService.updatePolicy(id, policyDTO);
        return ResponseEntity.ok(updatedPolicy);
    }
    
    @PostMapping("/policies")
    @PreAuthorize("hasRole('OFFICER')")
    public ResponseEntity<PolicyDTO> createPolicy(@Valid @RequestBody PolicyDTO policyDTO) {
        PolicyDTO created = policyService.createPolicy(policyDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}
