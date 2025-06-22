/*
 * Quote.java
 * 
 * Entity class representing insurance quotes.
 * Maps the quotes table with fields for linked proposal, quote amount,
 * and timestamp when the quote was generated.
 * Used to store and manage quote details corresponding to insurance proposals.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */
package com.hexaware.automobile.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "quotes")
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "proposal_id", unique = true)
    private Proposal proposal;

    private BigDecimal amount;

    @Column(name = "generated_on")
    private LocalDateTime generatedOn;

	public Quote(Long id, Proposal proposal, BigDecimal amount, LocalDateTime generatedOn) {
		super();
		this.id = id;
		this.proposal = proposal;
		this.amount = amount;
		this.generatedOn = generatedOn;
	}

	public Quote() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Proposal getProposal() {
		return proposal;
	}

	public void setProposal(Proposal proposal) {
		this.proposal = proposal;
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
