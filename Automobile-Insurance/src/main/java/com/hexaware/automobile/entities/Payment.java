/*
 * Payment.java
 * 
 * Entity class representing the payments table in the database.
 * Stores payment details linked to a unique quote, including payment status and timestamp.
 * Used to track the status and timing of payments for insurance quotes.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */
package com.hexaware.automobile.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "quote_id", unique = true)
    private Quote quote;

    @Column(name = "payment_status")
    private Boolean paymentStatus = false;

    @Column(name = "paid_on")
    private LocalDateTime paidOn;

	public Payment(Long id, Quote quote, Boolean paymentStatus, LocalDateTime paidOn) {
		super();
		this.id = id;
		this.quote = quote;
		this.paymentStatus = paymentStatus;
		this.paidOn = paidOn;
	}

	public Payment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Quote getQuote() {
		return quote;
	}

	public void setQuote(Quote quote) {
		this.quote = quote;
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
