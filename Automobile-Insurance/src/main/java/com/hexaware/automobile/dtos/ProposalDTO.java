/*
 * ProposalDTO.java
 * 
 * DTO for insurance proposal with validation on user ID, vehicle type, and status.
 * Includes timestamps for creation and updates.
 * Ensures backend processing only proceeds with valid, required fields.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */

package com.hexaware.automobile.dtos;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class ProposalDTO {

   
    private Long id;

    @NotNull(message = "User ID is mandatory")
    private Long userId;

    @NotBlank(message = "Vehicle type is mandatory")
    @Size(max = 50)
    private String vehicleType;

    @Pattern(regexp = "SUBMITTED|DOCUMENTS_REQUESTED|QUOTE_GENERATED|PAYMENT_PENDING|ACTIVE|REJECTED|EXPIRED",
             message = "Invalid status")
    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

    
}
