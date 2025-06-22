import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PaymentService } from '../../../core/services/payment.service';
import { AuthService } from '../../../core/services/auth.service';
import { PaymentDTO } from '../../../core/models/payment.dto';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {
  paymentForm: FormGroup; // For fetching payments
  createPaymentForm: FormGroup; // For creating payments
  payment: PaymentDTO | null = null;
  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private paymentService: PaymentService,
    private authService: AuthService
  ) {
    // Fetch payment form
    this.paymentForm = this.fb.group({
      paymentId: ['', [Validators.required, Validators.min(1)]]
    });

    // Create payment form
    this.createPaymentForm = this.fb.group({
      quoteId: ['', [Validators.required, Validators.pattern(/^\d+$/)]],
      paymentStatus: [false, Validators.required]
    });
  }

  ngOnInit(): void {
    const userId = this.authService.getUserId();
    if (!userId) {
      this.errorMessage = 'User not authenticated';
    }
  }

  fetchPaymentById(): void {
    if (this.paymentForm.invalid) {
      this.errorMessage = 'Please enter a valid Payment ID.';
      this.payment = null;
      return;
    }

    const paymentId = +this.paymentForm.value.paymentId;
    this.paymentService.getPaymentById(paymentId).subscribe({
      next: (payment) => {
        this.payment = payment;
        this.successMessage = 'Payment fetched successfully';
        this.errorMessage = null;
      },
      error: (err) => {
        this.payment = null;
        this.successMessage = null;
        this.errorMessage = err.error?.message || 'Payment not found';
      }
    });
  }

  createPayment(): void {
    if (this.createPaymentForm.invalid) {
      this.errorMessage = 'Please fill out all required fields correctly.';
      return;
    }

    const payment: PaymentDTO = {
      quoteId: +this.createPaymentForm.value.quoteId, // Convert to number
      paymentStatus: this.createPaymentForm.value.paymentStatus
    };

    console.log('Sending payment:', payment); // Debug payload
    this.paymentService.createPayment(payment).subscribe({
      next: (createdPayment) => {
        this.successMessage = 'Payment created successfully!';
        this.errorMessage = null;
        this.createPaymentForm.reset({ paymentStatus: false });
        this.payment = createdPayment; // Display created payment
      },
      error: (err) => {
        this.errorMessage = err.error?.message || 'Failed to create payment';
        this.successMessage = null;
        console.error('Create payment error:', err);
      }
    });
  }
}