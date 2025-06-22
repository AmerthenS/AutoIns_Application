/*
 * OfficerDTO.java
 * 
 * Transfers Officer data between client and server.
 * Ensures validation of fields such as password, role, name, and email using bean validation annotations.
 * Fields include:
 * - officerId: Unique identifier for the officer.
 * - password: Officer's password (mandatory, minimum 6 characters).
 * - role: Role assigned to the officer (mandatory).
 * - name: Officer's full name (mandatory).
 * - email: Officer's email address (mandatory, valid email format).
 * - jwtToken: JWT token for authentication responses.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */
package com.hexaware.automobile.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OfficerDTO {

    private Long officerId;

    

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Role is mandatory")
    private String role;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is mandatory")
    private String email;

   

    // JWT token for authentication responses
    private String jwtToken;

    public Long getOfficerId() {
        return officerId;
    }

    public void setOfficerId(Long officerId) {
        this.officerId = officerId;
    }

   

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
