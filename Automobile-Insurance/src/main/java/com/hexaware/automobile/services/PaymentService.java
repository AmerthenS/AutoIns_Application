/*
 * PaymentService.java
 * 
 * Service interface defining methods for managing payment records linked to quotes.
 * Provides operations to create, retrieve by ID or quote ID, update, list all, and delete payments.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */
package com.hexaware.automobile.services;

import com.hexaware.automobile.dtos.PaymentDTO;

import java.util.List;

public interface PaymentService {
    PaymentDTO createPayment(PaymentDTO dto);
    PaymentDTO getPaymentById(Long id);
    PaymentDTO getPaymentByQuoteId(Long quoteId);
    PaymentDTO updatePayment(Long id, PaymentDTO paymentDTO);
    List<PaymentDTO> getAllPayments();
    void deletePayment(Long id);
}
