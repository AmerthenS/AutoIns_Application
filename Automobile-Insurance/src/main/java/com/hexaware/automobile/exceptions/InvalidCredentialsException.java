/*
 * InvalidCredentialsException.java
 * 
 * Custom unchecked exception thrown when user login credentials are invalid.
 * Carries a specific error message to indicate authentication failure.
 * Helps in handling login errors cleanly and providing meaningful feedback.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */
package com.hexaware.automobile.exceptions;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
