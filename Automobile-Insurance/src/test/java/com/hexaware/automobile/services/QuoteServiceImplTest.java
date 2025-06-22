package com.hexaware.automobile.services;

import com.hexaware.automobile.dtos.QuoteDTO;
import com.hexaware.automobile.entities.Proposal;
import com.hexaware.automobile.entities.Quote;
import com.hexaware.automobile.exceptions.ResourceNotFoundException;
import com.hexaware.automobile.repositories.ProposalRepository;
import com.hexaware.automobile.repositories.QuoteRepository;
import com.hexaware.automobile.services.impl.QuoteServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuoteServiceImplTest {

    @InjectMocks
    private QuoteServiceImpl quoteService;

    @Mock
    private QuoteRepository quoteRepository;

    @Mock
    private ProposalRepository proposalRepository;

    private Quote quote;
    private QuoteDTO quoteDTO;
    private Proposal proposal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        proposal = new Proposal();
        proposal.setId(1L);

        quote = new Quote(100L, proposal, new BigDecimal("5000.00"), LocalDateTime.now());

        quoteDTO = new QuoteDTO();
        quoteDTO.setId(100L);
        quoteDTO.setProposalId(1L);
        quoteDTO.setAmount(new BigDecimal("5000.00"));
        quoteDTO.setGeneratedOn(LocalDateTime.now());
    }

    @Test
    void testCreateQuote() {
        when(proposalRepository.findById(1L)).thenReturn(Optional.of(proposal));
        when(quoteRepository.save(any(Quote.class))).thenAnswer(invocation -> invocation.getArgument(0));

        QuoteDTO created = quoteService.createQuote(quoteDTO);

        assertNotNull(created);
        assertEquals(quoteDTO.getAmount(), created.getAmount());
        verify(quoteRepository, times(1)).save(any(Quote.class));
    }

    @Test
    void testGetQuoteById_Success() {
        when(quoteRepository.findById(100L)).thenReturn(Optional.of(quote));

        QuoteDTO result = quoteService.getQuoteById(100L);

        assertNotNull(result);
        assertEquals(100L, result.getId());
    }

    @Test
    void testGetQuoteById_NotFound() {
        when(quoteRepository.findById(200L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> quoteService.getQuoteById(200L));
    }

    @Test
    void testGetQuoteByProposalId_Success() {
        Long proposalId = 1L;
        Quote quote = new Quote();
        quote.setId(100L);
        quote.setAmount(BigDecimal.valueOf(5000));
        quote.setGeneratedOn(LocalDateTime.now());
        
        Proposal proposal = new Proposal();
        proposal.setId(proposalId);
        quote.setProposal(proposal);

        when(quoteRepository.findByProposalId(proposalId)).thenReturn(Optional.of(quote));

        QuoteDTO result = quoteService.getQuoteByProposalId(proposalId);

        assertNotNull(result);
        assertEquals(100L, result.getId());
        assertEquals(proposalId, result.getProposalId());
    }


    @Test
    void testGetQuoteByProposalId_NotFound() {
        when(proposalRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> quoteService.getQuoteByProposalId(1L));
    }

    @Test
    void testGetAllQuotes() {
        when(quoteRepository.findAll()).thenReturn(Collections.singletonList(quote));

        List<QuoteDTO> all = quoteService.getAllQuotes();

        assertEquals(1, all.size());
    }

    @Test
    void testUpdateQuote_Success() {
        when(quoteRepository.findById(100L)).thenReturn(Optional.of(quote));
        when(proposalRepository.findById(1L)).thenReturn(Optional.of(proposal));
        when(quoteRepository.save(any(Quote.class))).thenAnswer(invocation -> invocation.getArgument(0));

        QuoteDTO updated = quoteService.updateQuote(100L, quoteDTO);

        assertNotNull(updated);
        assertEquals(quoteDTO.getAmount(), updated.getAmount());
    }

    @Test
    void testUpdateQuote_NotFound() {
        when(quoteRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> quoteService.updateQuote(100L, quoteDTO));
    }

    @Test
    void testDeleteQuote_Success() {
        when(quoteRepository.findById(100L)).thenReturn(Optional.of(quote));

        quoteService.deleteQuote(100L);

        verify(quoteRepository, times(1)).delete(quote);
    }

    @Test
    void testDeleteQuote_NotFound() {
        when(quoteRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> quoteService.deleteQuote(100L));
    }
}
