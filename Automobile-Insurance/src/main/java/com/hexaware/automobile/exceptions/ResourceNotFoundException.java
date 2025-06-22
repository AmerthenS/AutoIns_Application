/*
 * ResourceNotFoundException.java
 * 
 * Custom unchecked exception indicating that a requested resource was not found.
 * Contains a detailed error message to specify the missing resource.
 * Used to handle cases where entities or data are absent in the system.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */
package com.hexaware.automobile.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
