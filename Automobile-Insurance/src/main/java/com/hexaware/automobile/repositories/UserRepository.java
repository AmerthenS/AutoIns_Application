/*
 * UserRepository.java
 * 
 * Repository interface extending JpaRepository to manage User entities
 * with Long IDs. Provides methods to find users by their email address,
 * supporting both case-sensitive and case-insensitive searches.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */
package com.hexaware.automobile.repositories;

import com.hexaware.automobile.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    
    
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailIgnoreCase(String email);
}
