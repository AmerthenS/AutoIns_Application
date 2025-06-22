/*
 * Policy.java
 * 
 * Entity class representing insurance policies in the database.
 * Maps the policies table with fields for unique policy number, linked proposal, coverage dates,
 * PDF document URL, and current status (ACTIVE, EXPIRED, or REJECTED).
 * Used to store and manage issued insurance policies.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */

package com.hexaware.automobile.entities;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "policies")
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "proposal_id", unique = true)
    private Proposal proposal;

    @Column(name = "policy_number", unique = true)
    private String policyNumber;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "pdf_url")
    private String pdfUrl;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    public enum Status {
        ACTIVE,
        EXPIRED,
        CANCELLED
    }

	public Policy(Long id, Proposal proposal, String policyNumber, LocalDate startDate, LocalDate endDate,
			String pdfUrl, Status status) {
		super();
		this.id = id;
		this.proposal = proposal;
		this.policyNumber = policyNumber;
		this.startDate = startDate;
		this.endDate = endDate;
		this.pdfUrl = pdfUrl;
		this.status = status;
	}

	public Policy() {
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

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getPdfUrl() {
		return pdfUrl;
	}

	public void setPdfUrl(String pdfUrl) {
		this.pdfUrl = pdfUrl;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

    
}
