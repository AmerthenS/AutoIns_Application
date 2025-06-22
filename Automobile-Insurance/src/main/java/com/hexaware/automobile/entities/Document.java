/*
 * Document.java
 * 
 * Entity class representing the documents table in the database.
 * This entity stores document details associated with a Proposal, including document type,
 * file URL, verification status, and optional remarks.
 * It maintains a many-to-one relationship with the Proposal entity.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */
package com.hexaware.automobile.entities;
import jakarta.persistence.*;



@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "proposal_id")
    private Proposal proposal;

    @Column(name = "doc_type")
    private String docType;

    @Column(name = "file_url")
    private String fileUrl;

    private Boolean verified = false;

    private String remarks;

	public Document(Long id, Proposal proposal, String docType, String fileUrl, Boolean verified, String remarks) {
		super();
		this.id = id;
		this.proposal = proposal;
		this.docType = docType;
		this.fileUrl = fileUrl;
		this.verified = verified;
		this.remarks = remarks;
	}

	public Document() {
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

    
