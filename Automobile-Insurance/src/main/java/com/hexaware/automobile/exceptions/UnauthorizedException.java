/*
 * UnauthorizedException.java
 * 
 * Custom unchecked exception indicating an unauthorized access attempt.
 * Carries a specific error message to explain the cause of denial.
 * Used to enforce security by preventing access to protected resources.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */
package com.hexaware.automobile.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
