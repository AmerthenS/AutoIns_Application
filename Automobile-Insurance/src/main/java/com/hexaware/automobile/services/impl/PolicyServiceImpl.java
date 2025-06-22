/*
 * PolicyServiceImpl.java
 * 
 * Implements PolicyService interface providing CRUD operations for Policy entity.
 * 
 * Features:
 * - Validates existence of related Proposal before creating or updating Policy.
 * - Maps between Policy entity and PolicyDTO for data transfer.
 * - Supports fetching policies by Policy ID, Proposal ID, and User ID.
 * - Handles deletion and updates of Policy records with appropriate validations.
 * - Uses SLF4J logging to track operations and errors.
 * - Ensures transactional consistency for database operations.
 * - Throws ResourceNotFoundException for missing entities and handles invalid status values.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */
package com.hexaware.automobile.services.impl;

import com.hexaware.automobile.dtos.PolicyDTO;
import com.hexaware.automobile.entities.Policy;
import com.hexaware.automobile.entities.Proposal;
import com.hexaware.automobile.exceptions.ResourceNotFoundException;
import com.hexaware.automobile.repositories.PolicyRepository;
import com.hexaware.automobile.repositories.ProposalRepository;
import com.hexaware.automobile.services.PolicyService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PolicyServiceImpl implements PolicyService {

    private static final Logger logger = LoggerFactory.getLogger(PolicyServiceImpl.class);

    private final PolicyRepository policyRepository;
    private final ProposalRepository proposalRepository;

    public PolicyServiceImpl(PolicyRepository policyRepository, ProposalRepository proposalRepository) {
        this.policyRepository = policyRepository;
        this.proposalRepository = proposalRepository;
    }
    
    @Override
    public PolicyDTO createPolicy(PolicyDTO policyDTO) {
        logger.info("Creating new policy");

        Proposal proposal = proposalRepository.findById(policyDTO.getProposalId())
            .orElseThrow(() -> new ResourceNotFoundException("Proposal not found with id " + policyDTO.getProposalId()));

        Policy policy = new Policy();
        policy.setId(policyDTO.getId());
        policy.setProposal(proposal);
        policy.setPolicyNumber(policyDTO.getPolicyNumber());
        policy.setStartDate(policyDTO.getStartDate());
        policy.setEndDate(policyDTO.getEndDate());
        policy.setPdfUrl(policyDTO.getPdfUrl());

        try {
            policy.setStatus(Policy.Status.valueOf(policyDTO.getStatus()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + policyDTO.getStatus());
        }

        Policy savedPolicy = policyRepository.save(policy);
        logger.info("Policy created with id {}", savedPolicy.getId());

        return convertToDTO(savedPolicy);
    }
    
    @Override
    public PolicyDTO getPolicyByProposalId(Long proposalId) {
        logger.info("Fetching policy for proposalId: {}", proposalId);

        Policy policy = policyRepository.findByProposalId(proposalId)
            .orElseThrow(() -> new ResourceNotFoundException("Policy not found for proposalId: " + proposalId));

        return convertToDTO(policy);
    }



    @Override
    public List<PolicyDTO> getAllPolicies() {
        logger.info("Fetching all policies");
        return policyRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PolicyDTO getPolicyById(Long id) {
        logger.info("Fetching policy with id {}", id);
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found with id " + id));
        return convertToDTO(policy);
    }

    @Override
    public List<PolicyDTO> getPoliciesByUserId(Long userId) {
        List<Policy> policies = policyRepository.findByProposalUserId(userId);
        return policies.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    
    @Override
    public void deletePolicy(Long id) {
        logger.info("Deleting policy with id {}", id);
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found with id " + id));
        policyRepository.delete(policy);
        logger.info("Policy deleted successfully with id {}", id);
    }


    @Override
    public PolicyDTO updatePolicy(Long id, PolicyDTO dto) {
        logger.info("Updating policy with id {}", id);
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found with id " + id));

        Proposal proposal = proposalRepository.findById(dto.getProposalId())
                .orElseThrow(() -> new ResourceNotFoundException("Proposal not found with id " + dto.getProposalId()));

        policy.setProposal(proposal);
        policy.setStartDate(dto.getStartDate());
        policy.setEndDate(dto.getEndDate());
        policy.setPdfUrl(dto.getPdfUrl());
        policy.setStatus(Policy.Status.valueOf(dto.getStatus()));

        Policy updatedPolicy = policyRepository.save(policy);
        logger.info("Policy updated successfully for id {}", id);
        return convertToDTO(updatedPolicy);
    }

   

    private PolicyDTO convertToDTO(Policy policy) {
        PolicyDTO dto = new PolicyDTO();
        dto.setId(policy.getId());
        dto.setProposalId(policy.getProposal().getId());
        dto.setPolicyNumber(policy.getPolicyNumber());
        dto.setStartDate(policy.getStartDate());
        dto.setEndDate(policy.getEndDate());
        dto.setPdfUrl(policy.getPdfUrl());
        dto.setStatus(policy.getStatus().name());
        return dto;
    }

    private Policy convertToEntity(PolicyDTO dto, Proposal proposal) {
        Policy policy = new Policy();
        policy.setId(dto.getId());
        policy.setProposal(proposal);
        policy.setPolicyNumber(dto.getPolicyNumber());
        policy.setStartDate(dto.getStartDate());
        policy.setEndDate(dto.getEndDate());
        policy.setPdfUrl(dto.getPdfUrl());
        policy.setStatus(Policy.Status.valueOf(dto.getStatus()));
        return policy;
    }
}
