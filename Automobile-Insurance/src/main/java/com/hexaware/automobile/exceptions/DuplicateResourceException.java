/*
 * DuplicateResourceException.java
 * 
 * Custom unchecked exception to signal that an attempt to create or add a resource
 * has failed due to an existing duplicate resource in the system.
 * This exception carries a custom error message that explains the cause.
 * Used to enforce uniqueness constraints and handle conflicts gracefully.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */
package com.hexaware.automobile.exceptions;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
