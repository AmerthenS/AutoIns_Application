/*
 * QuoteServiceImpl.java
 * 
 * Implements QuoteService to handle CRUD operations for Quote entities.
 * 
 * Features:
 * - Validates existence of associated Proposal before creating or updating Quotes.
 * - Manages timestamp for quote generation, defaulting to current time if not provided.
 * - Converts between Quote entities and QuoteDTOs for API communication.
 * - Provides methods to fetch Quotes by ID, by Proposal ID, and retrieve all Quotes.
 * - Supports update and deletion operations with proper error handling.
 * - Uses SLF4J logging to trace method calls and key events.
 * - Ensures transactional consistency for create, update, and delete operations.
 * 
 * Throws ResourceNotFoundException when the requested Quote or Proposal does not exist.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */
package com.hexaware.automobile.services.impl;

import com.hexaware.automobile.dtos.QuoteDTO;
import com.hexaware.automobile.entities.Proposal;
import com.hexaware.automobile.entities.Quote;
import com.hexaware.automobile.exceptions.ResourceNotFoundException;
import com.hexaware.automobile.repositories.ProposalRepository;
import com.hexaware.automobile.repositories.QuoteRepository;
import com.hexaware.automobile.services.QuoteService;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

@Service
@Transactional
public class QuoteServiceImpl implements QuoteService {

    private static final Logger logger = LoggerFactory.getLogger(QuoteServiceImpl.class);

    private final QuoteRepository quoteRepository;
    private final ProposalRepository proposalRepository;

    public QuoteServiceImpl(QuoteRepository quoteRepository, ProposalRepository proposalRepository) {
        this.quoteRepository = quoteRepository;
        this.proposalRepository = proposalRepository;
    }

    @Override
    public QuoteDTO createQuote(QuoteDTO dto) {
        logger.info("Creating quote for proposal ID {}", dto.getProposalId());
        Quote quote = convertToEntity(dto);
        Quote saved = quoteRepository.save(quote);
        logger.info("Quote created with ID {}", saved.getId());
        return convertToDTO(saved);
    }

    @Override
    public QuoteDTO getQuoteById(Long id) {
        logger.info("Fetching quote with ID {}", id);
        Quote quote = quoteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quote not found with id " + id));
        return convertToDTO(quote);
    }

    @Override
    public QuoteDTO getQuoteByProposalId(Long proposalId) {
        logger.info("Fetching quote for proposal ID {}", proposalId);
        Quote quote = quoteRepository.findByProposalId(proposalId)
                .orElseThrow(() -> new ResourceNotFoundException("Quote not found for proposalId " + proposalId));
        return convertToDTO(quote);
    }

    @Override
    public List<QuoteDTO> getAllQuotes() {
        logger.info("Fetching all quotes");
        return quoteRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public QuoteDTO updateQuote(Long id, QuoteDTO dto) {
        logger.info("Updating quote with ID {}", id);
        Quote existing = quoteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quote not found with id " + id));

        Proposal proposal = proposalRepository.findById(dto.getProposalId())
                .orElseThrow(() -> new ResourceNotFoundException("Proposal not found with id " + dto.getProposalId()));

        existing.setProposal(proposal);
        existing.setAmount(dto.getAmount());
        existing.setGeneratedOn(dto.getGeneratedOn() != null ? dto.getGeneratedOn() : LocalDateTime.now());

        Quote updated = quoteRepository.save(existing);
        logger.info("Quote updated successfully for ID {}", id);
        return convertToDTO(updated);
    }

    @Override
    public void deleteQuote(Long id) {
        logger.info("Deleting quote with ID {}", id);
        Quote quote = quoteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quote not found with id " + id));
        quoteRepository.delete(quote);
        logger.info("Quote deleted successfully with ID {}", id);
    }
    
    

    
    private QuoteDTO convertToDTO(Quote quote) {
        QuoteDTO dto = new QuoteDTO();
        dto.setId(quote.getId());
        dto.setProposalId(quote.getProposal().getId());
        dto.setAmount(quote.getAmount());
        dto.setGeneratedOn(quote.getGeneratedOn());
        return dto;
    }

    
    private Quote convertToEntity(QuoteDTO dto) {
        Proposal proposal = proposalRepository.findById(dto.getProposalId())
                .orElseThrow(() -> new ResourceNotFoundException("Proposal not found with id " + dto.getProposalId()));

        Quote quote = new Quote();
        quote.setId(dto.getId());
        quote.setProposal(proposal);
        quote.setAmount(dto.getAmount());
        quote.setGeneratedOn(dto.getGeneratedOn() != null ? dto.getGeneratedOn() : LocalDateTime.now());
        return quote;
    }
}
