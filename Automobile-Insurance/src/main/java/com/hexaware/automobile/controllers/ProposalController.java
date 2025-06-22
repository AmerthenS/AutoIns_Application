/*
 * File: ProposalController.java
 * Author: Amerthen
 * Date: 2025-06-02
 * Description: Manages user proposals with role-based access control:
 *              - Users can create, view, and list their own proposals.
 *              - Officers have full control to update, delete, and retrieve all proposals.
 *              The controller maps entities to DTOs for clean API responses and
 *              logs key actions for traceability.
 */
package com.hexaware.automobile.controllers;

import com.hexaware.automobile.dtos.ProposalDTO;
import com.hexaware.automobile.entities.Proposal;
import com.hexaware.automobile.services.CustomUserDetails;
import com.hexaware.automobile.services.ProposalService;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/proposals")
public class ProposalController {

    private static final Logger logger = LoggerFactory.getLogger(ProposalController.class);

    private final ProposalService proposalService;

    public ProposalController(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

 
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<ProposalDTO> createProposal(@Valid @RequestBody ProposalDTO proposalDTO) {
        logger.info("User creating proposal");
        Proposal proposal = proposalService.createProposal(proposalDTO);
        return ResponseEntity.ok(mapEntityToDto(proposal));
    }

    
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ProposalDTO> getProposalById(@PathVariable Long id) {
        logger.info("User fetching proposal {}", id);
        Proposal proposal = proposalService.getProposalById(id);
        return ResponseEntity.ok(mapEntityToDto(proposal));
    }

    
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProposalDTO>> getProposalsByUserId(@PathVariable Long userId) {
        logger.info("User fetching proposals for userId {}", userId);
        List<Proposal> proposals = proposalService.getProposalsByUserId(userId);
        List<ProposalDTO> dtoList = proposals.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    
    @PreAuthorize("hasRole('OFFICER')")
    @PutMapping("/{id}")
    public ResponseEntity<ProposalDTO> updateProposal(@PathVariable Long id, @Valid @RequestBody ProposalDTO proposalDTO) {
        logger.info("Officer updating proposal {}", id);
        Proposal updatedProposal = proposalService.updateProposal(id, proposalDTO);
        return ResponseEntity.ok(mapEntityToDto(updatedProposal));
    }

   
    @PreAuthorize("hasRole('OFFICER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProposal(@PathVariable Long id) {
        logger.info("Officer deleting proposal {}", id);
        proposalService.deleteProposal(id);
        return ResponseEntity.noContent().build();
    }

    
    @PreAuthorize("hasRole('OFFICER')")
    @GetMapping
    public ResponseEntity<List<ProposalDTO>> getAllProposals() {
        logger.info("Officer fetching all proposals");
        List<Proposal> proposals = proposalService.getAllProposals();
        List<ProposalDTO> dtoList = proposals.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }
    
    

    
    private ProposalDTO mapEntityToDto(Proposal proposal) {
        ProposalDTO dto = new ProposalDTO();
        dto.setId(proposal.getId());
        dto.setUserId(proposal.getUser().getId());
        dto.setVehicleType(proposal.getVehicleType());
        dto.setStatus(proposal.getStatus().name());
        dto.setCreatedAt(proposal.getCreatedAt());
        dto.setUpdatedAt(proposal.getUpdatedAt());
        return dto;
    }
}

