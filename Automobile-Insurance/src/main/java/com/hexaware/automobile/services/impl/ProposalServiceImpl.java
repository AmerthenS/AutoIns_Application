/*
 * ProposalServiceImpl.java
 * 
 * Implements ProposalService to manage Proposal entities with full CRUD operations.
 * 
 * Key responsibilities:
 * - Validates existence of associated User before creating or updating Proposals.
 * - Manages Proposal status using enum with default to SUBMITTED.
 * - Handles creation and update timestamps automatically.
 * - Provides retrieval of Proposals by ID and by User ID.
 * - Deletes Proposals by ID.
 * - Uses SLF4J logging for tracing actions and debugging.
 * - Throws IllegalArgumentException when referenced User or Proposal is not found.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */
package com.hexaware.automobile.services.impl;

import com.hexaware.automobile.dtos.ProposalDTO;
import com.hexaware.automobile.entities.Proposal;
import com.hexaware.automobile.entities.User;
import com.hexaware.automobile.repositories.ProposalRepository;
import com.hexaware.automobile.repositories.UserRepository;
import com.hexaware.automobile.services.ProposalService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProposalServiceImpl implements ProposalService {

    private static final Logger logger = LoggerFactory.getLogger(ProposalServiceImpl.class);

    private final ProposalRepository proposalRepository;
    private final UserRepository userRepository;

    public ProposalServiceImpl(ProposalRepository proposalRepository, UserRepository userRepository) {
        this.proposalRepository = proposalRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Proposal createProposal(ProposalDTO dto) {
        logger.info("Creating proposal with ID {}", dto.getId());

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id " + dto.getUserId()));

        Proposal proposal = mapDtoToEntity(dto, user);

        proposal.setCreatedAt(LocalDateTime.now());
        proposal.setUpdatedAt(LocalDateTime.now());

        Proposal saved = proposalRepository.save(proposal);
        logger.info("Proposal created successfully with ID {}", saved.getId());
        return saved;
    }

    @Override
    public Proposal getProposalById(Long id) {
        logger.info("Fetching proposal by ID {}", id);
        return proposalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Proposal not found with id " + id));
    }

    @Override
    public Proposal updateProposal(Long id, ProposalDTO dto) {
        logger.info("Updating proposal with ID {}", id);
        Proposal existing = proposalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Proposal not found with id " + id));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id " + dto.getUserId()));

        // Update fields
        existing.setUser(user);
        existing.setVehicleType(dto.getVehicleType());
        existing.setStatus(Proposal.Status.valueOf(dto.getStatus()));
        existing.setUpdatedAt(LocalDateTime.now());

        Proposal updated = proposalRepository.save(existing);
        logger.info("Proposal updated successfully with ID {}", updated.getId());
        return updated;
    }

    @Override
    public void deleteProposal(Long id) {
        logger.info("Deleting proposal with ID {}", id);
        proposalRepository.deleteById(id);
    }

    @Override
    public List<Proposal> getAllProposals() {
        logger.info("Fetching all proposals");
        return proposalRepository.findAll();
    }

    @Override
    public List<Proposal> getProposalsByUserId(Long userId) {
        logger.info("Fetching proposals for user ID {}", userId);
        return proposalRepository.findByUserId(userId);
    }

    // Helper method to map DTO to Entity
    private Proposal mapDtoToEntity(ProposalDTO dto, User user) {
        Proposal proposal = new Proposal();
        proposal.setId(dto.getId());
        proposal.setUser(user);
        proposal.setVehicleType(dto.getVehicleType());
        proposal.setStatus(dto.getStatus() != null ? Proposal.Status.valueOf(dto.getStatus()) : Proposal.Status.SUBMITTED);
        proposal.setCreatedAt(dto.getCreatedAt());
        proposal.setUpdatedAt(dto.getUpdatedAt());
        return proposal;
    }
}