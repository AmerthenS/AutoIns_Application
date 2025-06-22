/*
 * PolicyDTO.java
 * 
 * DTO representing insurance policy data with validation on proposal ID, policy number, dates,
 * PDF URL, and status. Ensures backend receives valid and mandatory data before processing.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */
package com.hexaware.automobile.dtos;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class PolicyDTO {

   
    private Long id;

    @NotNull(message = "Proposal ID is mandatory")
    private Long proposalId;

    @NotBlank(message = "Policy number is mandatory")
    @Size(max = 100)
    private String policyNumber;

    @NotNull(message = "Start date is mandatory")
    private LocalDate startDate;

    @NotNull(message = "End date is mandatory")
    private LocalDate endDate;

    @Size(max = 255)
    private String pdfUrl;

    @Pattern(regexp = "ACTIVE|EXPIRED|CANCELLED", message = "Invalid status")
    private String status;

   

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
