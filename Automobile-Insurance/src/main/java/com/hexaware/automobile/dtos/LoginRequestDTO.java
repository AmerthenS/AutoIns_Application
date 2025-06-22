/*
 * LoginRequestDTO.java
 * 
 * author: Amerthen
 * date: 2025-06-02
 * 
 * Data Transfer Object for carrying user login credentials.
 * Includes validation to ensure:
 * - Email is present and follows a valid email format.
 * - Password is present (non-blank).
 * Used for authentication requests in the login process.
 */
package com.hexaware.automobile.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequestDTO {

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;

    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
