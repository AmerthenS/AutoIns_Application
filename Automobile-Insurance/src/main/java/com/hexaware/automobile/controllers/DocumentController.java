/*
 * File: DocumentController.java
 * Author: Amerthen
 * Date: 2025-06-02
 * Description: Provides secured REST endpoints for officers to upload, fetch,
 *              and verify insurance-related documents based on proposals.
 */

package com.hexaware.automobile.controllers;


import com.hexaware.automobile.dtos.DocumentDTO;
import com.hexaware.automobile.entities.Document;
import com.hexaware.automobile.services.CustomUserDetails;
import com.hexaware.automobile.services.DocumentService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    private DocumentService documentService;

    
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Document> uploadDocument(@Valid @RequestBody DocumentDTO documentDTO) {
        logger.info("Uploading document for proposal ID {}", documentDTO.getProposalId());
        Document savedDocument = documentService.uploadDocument(documentDTO);
        return ResponseEntity.ok(savedDocument);
    }

    
    @GetMapping("/proposal/{proposalId}")
    @PreAuthorize("hasRole('OFFICER')")
    public ResponseEntity<List<DocumentDTO>> getDocumentsByProposalId(@PathVariable Long proposalId) {
        logger.info("Fetching documents for proposal ID {}", proposalId);
        List<DocumentDTO> documents = documentService.getDocumentsByProposalId(proposalId);
        return ResponseEntity.ok(documents);
    }

    
    @PutMapping("/{id}/verify")
    @PreAuthorize("hasRole('OFFICER')")
    public ResponseEntity<Document> verifyDocument(
            @PathVariable Long id,
            @RequestParam boolean verified,
            @RequestParam(required = false) String remarks) {
        
        logger.info("Verifying document ID {} - status: {}, remarks: {}", id, verified, remarks);
        Document updatedDocument = documentService.verifyDocument(id, verified, remarks);
        return ResponseEntity.ok(updatedDocument);
    }
    
    @GetMapping("/my")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<DocumentDTO>> getMyDocuments() {
        logger.info("Fetching documents for authenticated user");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long userId = extractUserIdFromAuthentication(auth);
        List<DocumentDTO> documents = documentService.getDocumentsByUserId(userId);
        return ResponseEntity.ok(documents);
    }

    private Long extractUserIdFromAuthentication(Authentication auth) {
        Object principal = auth.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getUserId();
        }
        throw new IllegalStateException("User ID not found in authentication principal");
    }
}
