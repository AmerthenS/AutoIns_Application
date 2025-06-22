package com.hexaware.automobile.services;

import com.hexaware.automobile.dtos.DocumentDTO;
import com.hexaware.automobile.entities.Document;
import com.hexaware.automobile.entities.Proposal;
import com.hexaware.automobile.exceptions.ResourceNotFoundException;
import com.hexaware.automobile.repositories.DocumentRepository;
import com.hexaware.automobile.repositories.ProposalRepository;
import com.hexaware.automobile.services.impl.DocumentServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DocumentServiceImplTest {

    @InjectMocks
    private DocumentServiceImpl documentService;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private ProposalRepository proposalRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUploadDocument_Success() {
        DocumentDTO dto = new DocumentDTO();
        dto.setId(1L);
        dto.setProposalId(100L);
        dto.setDocType("RC");
        dto.setFileUrl("https://some-url.com");
        dto.setVerified(false);
        dto.setRemarks("Initial upload");

        Proposal proposal = new Proposal();
        proposal.setId(100L);

        when(proposalRepository.findById(100L)).thenReturn(Optional.of(proposal));
        when(documentRepository.save(any(Document.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Document result = documentService.uploadDocument(dto);

        assertNotNull(result);
        assertEquals("RC", result.getDocType());
        assertEquals("https://some-url.com", result.getFileUrl());
        verify(documentRepository, times(1)).save(any(Document.class));
    }

    @Test
    void testUploadDocument_ProposalNotFound() {
        DocumentDTO dto = new DocumentDTO();
        dto.setProposalId(999L);

        when(proposalRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            documentService.uploadDocument(dto);
        });
    }

    @Test
    void testGetDocumentsByProposalId_Success() {
        Long proposalId = 200L;
        List<Document> docs = Arrays.asList(
                new Document(1L, new Proposal(), "RC", "url1", false, "Remark 1"),
                new Document(2L, new Proposal(), "DL", "url2", true, "Remark 2")
        );

        when(documentRepository.findByProposalId(proposalId)).thenReturn(docs);

        List<DocumentDTO> result = documentService.getDocumentsByProposalId(proposalId);

        assertEquals(2, result.size());
        assertEquals("RC", result.get(0).getDocType());
        assertEquals("DL", result.get(1).getDocType());
        verify(documentRepository, times(1)).findByProposalId(proposalId);
    }

    @Test
    void testVerifyDocument_Success() {
        Long docId = 1L;
        Document doc = new Document(docId, new Proposal(), "RC", "url", false, "old");

        when(documentRepository.findById(docId)).thenReturn(Optional.of(doc));
        when(documentRepository.save(any(Document.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Document result = documentService.verifyDocument(docId, true, "Verified by officer");

        assertTrue(result.getVerified());
        assertEquals("Verified by officer", result.getRemarks());
        verify(documentRepository, times(1)).save(doc);
    }

    @Test
    void testVerifyDocument_NotFound() {
        when(documentRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            documentService.verifyDocument(10L, true, "Remarks");
        });
    }
}
