/*
 * DocumentService.java
 * 
 * Service interface defining operations for managing documents in the system.
 * Provides methods to upload documents, retrieve documents by proposal ID,
 * and verify documents by updating their verification status and remarks.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */
package com.hexaware.automobile.services;

import com.hexaware.automobile.dtos.DocumentDTO;
import com.hexaware.automobile.entities.Document;

import java.util.List;

public interface DocumentService {
    Document uploadDocument(DocumentDTO documentDTO);
    List<DocumentDTO> getDocumentsByProposalId(Long proposalId); 
    List<DocumentDTO> getDocumentsByUserId(Long userId);
    Document verifyDocument(Long id, boolean isVerified, String remarks);
}

