/*
 * QuoteService.java
 * 
 * Service interface defining CRUD operations for Quote entities.
 * Provides methods to create, retrieve by ID or proposal ID, update, delete,
 * and fetch all quotes.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */
package com.hexaware.automobile.services;

import com.hexaware.automobile.dtos.QuoteDTO;
import java.util.List;

public interface QuoteService {
    QuoteDTO createQuote(QuoteDTO quoteDTO);
    QuoteDTO getQuoteById(Long id);
    QuoteDTO getQuoteByProposalId(Long proposalId);
    List<QuoteDTO> getAllQuotes();
    QuoteDTO updateQuote(Long id, QuoteDTO quoteDTO);
    void deleteQuote(Long id);
}
