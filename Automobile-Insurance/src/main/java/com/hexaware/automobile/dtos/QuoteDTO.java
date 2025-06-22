/*
 * QuoteDTO.java
 * 
 * DTO for insurance quote with validation on proposal ID and positive amount.
 * Includes timestamp for when the quote was generated.
 * Ensures all required fields are valid before backend processing.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */

package com.hexaware.automobile.dtos;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class QuoteDTO {

    
    private Long id;

    @NotNull(message = "Proposal ID is mandatory")
    private Long proposalId;

    @NotNull(message = "Amount is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be positive")
    private BigDecimal amount;

    private LocalDateTime generatedOn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProposalId() {
		return proposalId;
	}

	public void setProposalId(Long proposalId) {
		this.proposalId = proposalId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDateTime getGeneratedOn() {
		return generatedOn;
	}

	public void setGeneratedOn(LocalDateTime generatedOn) {
		this.generatedOn = generatedOn;
	}

    
}
