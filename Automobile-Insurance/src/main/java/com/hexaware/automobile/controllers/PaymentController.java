/*
 * File: PaymentController.java
 * Author: Amerthen
 * Date: 2025-06-02
 * Description: Provides secured REST endpoints to create, retrieve, update, and delete payment records,
 *              with role-based access control (users can create/update, officers can view/delete all payments)
 *              and detailed logging for each operation.
 */
package com.hexaware.automobile.controllers;

import com.hexaware.automobile.dtos.PaymentDTO;
import com.hexaware.automobile.dtos.QuoteDTO;
import com.hexaware.automobile.entities.Quote;
import com.hexaware.automobile.exceptions.ResourceNotFoundException;
import com.hexaware.automobile.repositories.QuoteRepository;
import com.hexaware.automobile.services.PaymentService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PaymentDTO> createPayment(@Valid @RequestBody PaymentDTO paymentDTO) {
        logger.info("Creating payment with ID: {}", paymentDTO.getId());
        PaymentDTO created = paymentService.createPayment(paymentDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    @GetMapping("/byquote/{quoteId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PaymentDTO> getPaymentByQuoteId(@PathVariable Long quoteId) {
        logger.info("Fetching payment for quoteId: {}", quoteId);
        try {
            PaymentDTO payment = paymentService.getPaymentByQuoteId(quoteId);
            return ResponseEntity.ok(payment);
        } catch (ResourceNotFoundException e) {
            logger.warn("No payment found for quoteId: {}", quoteId);
            return ResponseEntity.notFound().build();
        }
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Long id) {
        logger.info("Fetching payment with ID: {}", id);
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    
    @GetMapping
    @PreAuthorize("hasRole('OFFICER')")
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        logger.info("Fetching all payments");
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PaymentDTO> updatePayment(
            @PathVariable Long id,
            @Valid @RequestBody PaymentDTO paymentDTO) {
        logger.info("Updating payment with ID: {}", id);
        return ResponseEntity.ok(paymentService.updatePayment(id, paymentDTO));
    }

    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OFFICER')")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        logger.info("Deleting payment with ID: {}", id);
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
