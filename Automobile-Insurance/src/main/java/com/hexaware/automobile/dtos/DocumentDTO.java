/*
 * DocumentDTO.java
 * 
 * Acts as a Data Transfer Object to carry document information associated with a proposal.
 * Ensures backend processing receives valid and required fields through bean validation annotations.
 * Fields include:
 * - id: Document identifier.
 * - proposalId: Associated proposal's ID (mandatory).
 * - docType: Type of document (mandatory, max 50 chars).
 * - fileUrl: URL or path of the stored file (mandatory, max 255 chars).
 * - verified: Flag indicating if the document is verified.
 * - remarks: Optional remarks or comments (max 255 chars).
 */
package com.hexaware.automobile.dtos;

import jakarta.validation.constraints.*;

public class DocumentDTO {

    
    private Long id;

    @NotNull(message = "Proposal ID is mandatory")
    private Long proposalId;

    @NotBlank(message = "Document type is mandatory")
    @Size(max = 50)
    private String docType;

    @NotBlank(message = "File URL is mandatory")
    @Size(max = 255)
    private String fileUrl;

    private Boolean verified;

    @Size(max = 255)
    private String remarks;

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

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

    
}
