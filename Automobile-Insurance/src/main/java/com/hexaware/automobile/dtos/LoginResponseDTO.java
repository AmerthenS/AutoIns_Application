/*
 * LoginResponseDTO.java
 * 
 * Data Transfer Object that encapsulates the JWT token issued after a successful login,
 * along with a message providing additional information to the client.
 * This object is returned as the response payload for authentication requests.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */

package com.hexaware.automobile.dtos;

public class LoginResponseDTO {
    private String token;
    private String message;

    public LoginResponseDTO(String token, String message) {
        this.token = token;
        this.message = message;
    }

    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
