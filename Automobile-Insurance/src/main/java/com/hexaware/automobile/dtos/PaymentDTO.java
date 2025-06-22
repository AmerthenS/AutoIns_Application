/*
 * PaymentDTO.java
 * 
 * DTO for payment details linked to a quote with validation on quoteId, payment status, and payment timestamp.
 * Ensures required fields are validated before backend processing.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */

package com.hexaware.automobile.dtos;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class PaymentDTO {

    
    private Long id;

    @NotNull(message = "Quote ID is mandatory")
    private Long quoteId;

    private Boolean paymentStatus;

    private LocalDateTime paidOn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(Long quoteId) {
		this.quoteId = quoteId;
	}

	public Boolean getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public LocalDateTime getPaidOn() {
		return paidOn;
	}

	public void setPaidOn(LocalDateTime paidOn) {
		this.paidOn = paidOn;
	}

    
}

