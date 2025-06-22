/*
 * OfficerRepository.java
 * 
 * Repository interface extending JpaRepository to provide CRUD operations
 * for Officer entities with Long IDs. Includes methods to find an officer
 * by email (case-sensitive and case-insensitive) and to check the existence
 * of an officer's email.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */
package com.hexaware.automobile.repositories;

import com.hexaware.automobile.entities.Officer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OfficerRepository extends JpaRepository<Officer, Long> {

    Optional<Officer> findByEmail(String email);

    boolean existsByEmail(String email);
    
    Optional<Officer> findByEmailIgnoreCase(String email);
}
