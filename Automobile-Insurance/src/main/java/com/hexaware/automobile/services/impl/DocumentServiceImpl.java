/*
 * File: DocumentServiceImpl.java
 * Author: Amerthen
 * Date: 2025-06-02
 * Description: Service implementation for Document entity business logic including:
 * - Uploading documents linked to a Proposal
 * - Fetching documents by Proposal ID or User ID
 * - Verifying documents (restricted to users with OFFICER role)
 * Maps between Document and DocumentDTO, logs key operations,
 * and throws ResourceNotFoundException when Proposal or Document is not found.
 */
package com.hexaware.automobile.services.impl;

import com.hexaware.automobile.dtos.DocumentDTO;
import com.hexaware.automobile.entities.Document;
import com.hexaware.automobile.entities.Proposal;
import com.hexaware.automobile.exceptions.ResourceNotFoundException;
import com.hexaware.automobile.repositories.DocumentRepository;
import com.hexaware.automobile.repositories.ProposalRepository;
import com.hexaware.automobile.services.DocumentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {

    private static final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ProposalRepository proposalRepository;

    @Override
    public Document uploadDocument(DocumentDTO dto) {
        logger.info("Uploading document with ID: {}", dto.getId());

        Proposal proposal = proposalRepository.findById(dto.getProposalId())
                .orElseThrow(() -> new ResourceNotFoundException("Proposal not found with ID: " + dto.getProposalId()));

        Document document = mapToEntity(dto, proposal);
        return documentRepository.save(document);
    }

    @Override
    public List<DocumentDTO> getDocumentsByProposalId(Long proposalId) {
        logger.info("Fetching documents for Proposal ID: {}", proposalId);

        List<Document> documents = documentRepository.findByProposalId(proposalId);
        return documents.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<DocumentDTO> getDocumentsByUserId(Long userId) {
        logger.info("Fetching documents for User ID: {}", userId);

        List<Document> documents = documentRepository.findByProposalUserId(userId);
        return documents.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('OFFICER')")
    public Document verifyDocument(Long id, boolean isVerified, String remarks) {
        logger.info("Verifying document with ID: {}", id);

        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with ID: " + id));

        document.setVerified(isVerified);
        document.setRemarks(remarks);

        return documentRepository.save(document);
    }

    // Helper Methods
    private Document mapToEntity(DocumentDTO dto, Proposal proposal) {
        Document document = new Document();
        document.setId(dto.getId());
        document.setProposal(proposal);
        document.setDocType(dto.getDocType());
        document.setFileUrl(dto.getFileUrl());
        document.setVerified(dto.getVerified() != null ? dto.getVerified() : false);
        document.setRemarks(dto.getRemarks());
        return document;
    }

    private DocumentDTO mapToDTO(Document document) {
        DocumentDTO dto = new DocumentDTO();
        dto.setId(document.getId());
        dto.setProposalId(document.getProposal().getId());
        dto.setDocType(document.getDocType());
        dto.setFileUrl(document.getFileUrl());
        dto.setVerified(document.getVerified());
        dto.setRemarks(document.getRemarks());
        return dto;
    }
}