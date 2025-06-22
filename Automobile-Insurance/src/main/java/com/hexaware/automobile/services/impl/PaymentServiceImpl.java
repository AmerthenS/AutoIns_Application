/*
 * PaymentServiceImpl.java
 * 
 * Implements PaymentService interface with CRUD operations for Payment entity.
 * 
 * Key features:
 * - Maps between PaymentDTO and Payment entity, including Quote association.
 * - Validates existence of associated Quote before creating or updating Payment.
 * - Creates, retrieves (by Payment ID or Quote ID), updates, and deletes Payment records.
 * - Retrieves all Payment records and converts them to DTOs.
 * - Uses SLF4J logging for tracing operations and error handling.
 * - Throws ResourceNotFoundException when referenced entities are missing.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */

package com.hexaware.automobile.services.impl;

import com.hexaware.automobile.dtos.PaymentDTO;
import com.hexaware.automobile.entities.Payment;
import com.hexaware.automobile.entities.Quote;
import com.hexaware.automobile.exceptions.ResourceNotFoundException;
import com.hexaware.automobile.repositories.PaymentRepository;
import com.hexaware.automobile.repositories.QuoteRepository;
import com.hexaware.automobile.services.PaymentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private QuoteRepository quoteRepository;

    
    private PaymentDTO mapToDTO(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setQuoteId(payment.getQuote().getId());
        dto.setPaymentStatus(payment.getPaymentStatus());
        dto.setPaidOn(payment.getPaidOn());
        return dto;
    }

    
    private Payment mapToEntity(PaymentDTO dto) {
        Quote quote = quoteRepository.findById(dto.getQuoteId())
                .orElseThrow(() -> {
                    logger.error("Quote not found with id {}", dto.getQuoteId());
                    return new ResourceNotFoundException("Quote not found with id " + dto.getQuoteId());
                });

        Payment payment = new Payment();
        payment.setId(dto.getId());
        payment.setQuote(quote);
        payment.setPaymentStatus(dto.getPaymentStatus());
        payment.setPaidOn(dto.getPaidOn());
        return payment;
    }

    @Override
    public PaymentDTO createPayment(PaymentDTO dto) {
        logger.info("Creating new payment for quoteId: {}", dto.getQuoteId());
        Payment saved = paymentRepository.save(mapToEntity(dto));
        logger.debug("Payment saved with ID: {}", saved.getId());
        return mapToDTO(saved);
    }

    @Override
    public PaymentDTO getPaymentById(Long id) {
        logger.info("Fetching payment by ID: {}", id);
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Payment not found with ID: {}", id);
                    return new ResourceNotFoundException("Payment not found with id " + id);
                });
        return mapToDTO(payment);
    }

    @Override
    public PaymentDTO getPaymentByQuoteId(Long quoteId) {
        logger.info("Fetching payment by Quote ID: {}", quoteId);
        Quote quote = quoteRepository.findById(quoteId)
                .orElseThrow(() -> {
                    logger.error("Quote not found with ID: {}", quoteId);
                    return new ResourceNotFoundException("Quote not found with id " + quoteId);
                });

        Payment payment = paymentRepository.findByQuote(quote)
                .orElseThrow(() -> {
                    logger.warn("Payment not found for Quote ID: {}", quoteId);
                    return new ResourceNotFoundException("Payment not found for quoteId " + quoteId);
                });

        return mapToDTO(payment);
    }
    @Override
    public PaymentDTO updatePayment(Long id, PaymentDTO paymentDTO) {
        logger.info("Updating payment with ID: {}", id);

        Payment existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id " + id));

        
        existingPayment.setPaymentStatus(paymentDTO.getPaymentStatus());
        existingPayment.setPaidOn(paymentDTO.getPaidOn());

        
        if (!existingPayment.getQuote().getId().equals(paymentDTO.getQuoteId())) {
            Quote quote = quoteRepository.findById(paymentDTO.getQuoteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Quote not found with id " + paymentDTO.getQuoteId()));
            existingPayment.setQuote(quote);
        }

        Payment updatedPayment = paymentRepository.save(existingPayment);

        logger.info("Payment updated successfully with ID: {}", updatedPayment.getId());

        return mapToDTO(updatedPayment);
    }



    @Override
    public List<PaymentDTO> getAllPayments() {
        logger.info("Fetching all payments");
        return paymentRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deletePayment(Long id) {
        logger.info("Deleting payment with ID: {}", id);
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Payment not found for deletion with ID: {}", id);
                    return new ResourceNotFoundException("Payment not found with id " + id);
                });
        paymentRepository.delete(payment);
        logger.info("Payment deleted with ID: {}", id);
    }
}
