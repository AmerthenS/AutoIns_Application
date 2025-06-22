package com.hexaware.automobile.services;

import com.hexaware.automobile.dtos.PaymentDTO;
import com.hexaware.automobile.entities.Payment;
import com.hexaware.automobile.entities.Quote;
import com.hexaware.automobile.exceptions.ResourceNotFoundException;
import com.hexaware.automobile.repositories.PaymentRepository;
import com.hexaware.automobile.repositories.QuoteRepository;
import com.hexaware.automobile.services.impl.PaymentServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PaymentServiceImplTest {

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private QuoteRepository quoteRepository;

    private Quote sampleQuote;
    private Payment samplePayment;
    private PaymentDTO samplePaymentDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleQuote = new Quote();
        sampleQuote.setId(1L);

        samplePayment = new Payment();
        samplePayment.setId(1L);
        samplePayment.setQuote(sampleQuote);
        samplePayment.setPaymentStatus(true);
        samplePayment.setPaidOn(LocalDateTime.now());

        samplePaymentDTO = new PaymentDTO();
        samplePaymentDTO.setId(1L);
        samplePaymentDTO.setQuoteId(1L);
        samplePaymentDTO.setPaymentStatus(true);
        samplePaymentDTO.setPaidOn(samplePayment.getPaidOn());
    }

    @Test
    void testCreatePayment() {
        when(quoteRepository.findById(1L)).thenReturn(Optional.of(sampleQuote));
        when(paymentRepository.save(any(Payment.class))).thenReturn(samplePayment);

        PaymentDTO result = paymentService.createPayment(samplePaymentDTO);

        assertEquals(samplePaymentDTO.getId(), result.getId());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testGetPaymentById_Success() {
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(samplePayment));

        PaymentDTO result = paymentService.getPaymentById(1L);

        assertEquals(samplePayment.getId(), result.getId());
        assertTrue(result.getPaymentStatus());
        verify(paymentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetPaymentById_NotFound() {
        when(paymentRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> paymentService.getPaymentById(2L));
    }

    @Test
    void testGetAllPayments() {
        List<Payment> payments = List.of(samplePayment);
        when(paymentRepository.findAll()).thenReturn(payments);

        List<PaymentDTO> result = paymentService.getAllPayments();

        assertEquals(1, result.size());
        assertEquals(samplePayment.getId(), result.get(0).getId());
    }

    @Test
    void testUpdatePayment_Success() {
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(samplePayment));
        when(quoteRepository.findById(1L)).thenReturn(Optional.of(sampleQuote));
        when(paymentRepository.save(any(Payment.class))).thenReturn(samplePayment);

        PaymentDTO updatedDTO = new PaymentDTO();
        updatedDTO.setId(1L);
        updatedDTO.setQuoteId(1L);
        updatedDTO.setPaymentStatus(false);
        updatedDTO.setPaidOn(LocalDateTime.now());

        PaymentDTO result = paymentService.updatePayment(1L, updatedDTO);

        assertEquals(1L, result.getId());
        assertFalse(result.getPaymentStatus());
    }

    @Test
    void testDeletePayment() {
        Long id = 1L;
        Payment mockPayment = new Payment();
        mockPayment.setId(id);

        when(paymentRepository.findById(id)).thenReturn(Optional.of(mockPayment));

        paymentService.deletePayment(id);

        
        verify(paymentRepository, times(1)).findById(id);
        verify(paymentRepository, times(1)).delete(mockPayment);
    }

    
}
