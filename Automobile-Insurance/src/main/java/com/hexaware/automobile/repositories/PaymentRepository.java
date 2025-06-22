/*
 * PaymentRepository.java
 * 
 * Repository interface extending JpaRepository to manage Payment entities
 * with Long IDs. Provides a method to find a Payment by its associated
 * Quote entity.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */
package com.hexaware.automobile.repositories;

import com.hexaware.automobile.entities.Payment;
import com.hexaware.automobile.entities.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByQuote(Quote quote);
    
    
}
