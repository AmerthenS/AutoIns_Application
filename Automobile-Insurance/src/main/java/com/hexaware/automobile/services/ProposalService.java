/*
 * ProposalService.java
 * 
 * Service interface defining operations for managing insurance proposals.
 * Includes methods to create, retrieve by ID, update, delete, list all proposals,
 * and fetch proposals by associated user ID.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */
package com.hexaware.automobile.services;

import com.hexaware.automobile.dtos.ProposalDTO;
import com.hexaware.automobile.entities.Proposal;

import java.util.List;

public interface ProposalService {
    Proposal createProposal(ProposalDTO proposalDTO);
    Proposal getProposalById(Long id);
    Proposal updateProposal(Long id, ProposalDTO proposalDTO);
    void deleteProposal(Long id);
    List<Proposal> getAllProposals();
    List<Proposal> getProposalsByUserId(Long userId);
}

