/*
 * File: QuoteController.java
 * Author: Amerthen
 * Date: 2025-06-02
 * Description: Handles quote management with role-based access control:
 *              - Officers can create, update, delete, and list all quotes.
 *              - Both users and officers can fetch quotes by ID, by associated proposal, or for the authenticated user.
 *              Key operations are logged for audit and debugging purposes.
 */
package com.hexaware.automobile.controllers;

import com.hexaware.automobile.dtos.QuoteDTO;
import com.hexaware.automobile.services.QuoteService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quotes")
public class QuoteController {

    private static final Logger logger = LoggerFactory.getLogger(QuoteController.class);

    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @PostMapping
    @PreAuthorize("hasRole('OFFICER')")
    public ResponseEntity<QuoteDTO> createQuote(@Valid @RequestBody QuoteDTO quoteDTO) {
        logger.info("Received request to create quote for proposalId {}", quoteDTO.getProposalId());
        QuoteDTO created = quoteService.createQuote(quoteDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    

    @GetMapping("/{id:\\d+}")
    @PreAuthorize("hasAnyRole('USER', 'OFFICER')")
    public ResponseEntity<QuoteDTO> getQuoteById(@PathVariable Long id) {
        logger.info("Fetching quote by ID {}", id);
        return ResponseEntity.ok(quoteService.getQuoteById(id));
    }

    @GetMapping("/proposal/{proposalId}")
    @PreAuthorize("hasAnyRole('USER', 'OFFICER')")
    public ResponseEntity<QuoteDTO> getQuoteByProposalId(@PathVariable Long proposalId) {
        logger.info("Fetching quote for proposalId {}", proposalId);
        return ResponseEntity.ok(quoteService.getQuoteByProposalId(proposalId));
    }

    @GetMapping
    @PreAuthorize("hasRole('OFFICER')")
    public ResponseEntity<List<QuoteDTO>> getAllQuotes() {
        logger.info("Fetching all quotes");
        return ResponseEntity.ok(quoteService.getAllQuotes());
    }

    @PutMapping("/{id:\\d+}")
    @PreAuthorize("hasRole('OFFICER')")
    public ResponseEntity<QuoteDTO> updateQuote(@PathVariable Long id, @Valid @RequestBody QuoteDTO quoteDTO) {
        logger.info("Updating quote with id {}", id);
        return ResponseEntity.ok(quoteService.updateQuote(id, quoteDTO));
    }

    @DeleteMapping("/{id:\\d+}")
    @PreAuthorize("hasRole('OFFICER')")
    public ResponseEntity<Void> deleteQuote(@PathVariable Long id) {
        logger.info("Deleting quote with id {}", id);
        quoteService.deleteQuote(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

  
}