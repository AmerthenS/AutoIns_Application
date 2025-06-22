/*
 * UserDTO.java
 * 
 * Represents user data transferred between client and server with validation 
 * on fields such as name, email, password, Aadhaar number, PAN number, date of birth, and role.
 * Includes a derived read-only age property calculated from the date of birth.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */
package com.hexaware.automobile.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.Period;

public class UserDTO {
	
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id; 

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @Size(max = 255, message = "Address too long")
    private String address;

    @NotBlank(message = "Aadhaar number is required")
    @Pattern(regexp = "\\d{12}", message = "Aadhaar number must be exactly 12 digits")
    private String aadhaarNumber;

    @NotBlank(message = "PAN number is required")
    @Pattern(regexp = "[A-Z]{5}\\d{4}[A-Z]", message = "PAN number must be valid")
    private String panNumber;

    @NotNull(message = "Date of birth is required")
    private LocalDate dob;

    @NotNull(message = "Role is required")
    private Role role;

    private String jwtToken;

    public enum Role {
        ROLE_USER,
        ROLE_OFFICER
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Integer getAge() {
        if (dob == null) return 0;
        return Period.between(dob, LocalDate.now()).getYears();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getAadhaarNumber() { return aadhaarNumber; }
    public void setAadhaarNumber(String aadhaarNumber) { this.aadhaarNumber = aadhaarNumber; }
    public String getPanNumber() { return panNumber; }
    public void setPanNumber(String panNumber) { this.panNumber = panNumber; }
    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public String getJwtToken() { return jwtToken; }
    public void setJwtToken(String jwtToken) { this.jwtToken = jwtToken; }
}