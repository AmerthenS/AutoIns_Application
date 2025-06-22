import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../core/services/auth.service';
import { UserService } from '../../../core/services/user.service';
import { ProposalService } from '../../../core/services/proposal.service';
import { PaymentService } from '../../../core/services/payment.service';
import { PolicyService } from '../../../core/services/policy.service';
import { UserDTO } from '../../../core/models/user.dto';
import { ProposalDTO } from '../../../core/models/proposal.dto';
import { PaymentDTO } from '../../../core/models/payment.dto';
import { PolicyDTO } from '../../../core/models/policy.dto';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  user: UserDTO | null = null;
  proposals: ProposalDTO[] = [];
  payments: PaymentDTO[] = [];
  policies: PolicyDTO[] = [];
  errorMessage: string | null = null;
  successMessage: string | null = null;
  quoteIdInput: string = '';

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private proposalService: ProposalService,
    private paymentService: PaymentService,
    private policyService: PolicyService
  ) {}

  ngOnInit(): void {
    const userId = this.authService.getUserId();
    if (userId) {
      this.userService.getUserById(userId).subscribe({
        next: (user) => this.user = user,
        error: (err) => this.errorMessage = err.error?.message || 'Failed to load user data'
      });
      this.proposalService.getProposalsByUserId(userId).subscribe({
        next: (proposals) => this.proposals = proposals,
        error: (err) => this.errorMessage = err.error?.message || 'Failed to load proposals'
      });
      this.policyService.getMyPolicies().subscribe({
        next: (policies) => this.policies = policies,
        error: (err) => this.errorMessage = err.error?.message || 'Failed to load policies'
      });
    } else {
      this.errorMessage = 'User not authenticated';
    }
  }

  fetchPayment(): void {
    const quoteId = parseInt(this.quoteIdInput, 10);
    if (isNaN(quoteId) || quoteId <= 0) {
      this.errorMessage = 'Please enter a valid Quote ID';
      return;
    }
    this.errorMessage = null;
    this.paymentService.getPaymentByQuoteId(quoteId).subscribe({
      next: (payment) => {
        if (payment) {
          this.payments = [...this.payments.filter(p => p.quoteId !== quoteId), payment];
          this.successMessage = `Payment for Quote ID ${quoteId} fetched successfully`;
        } else {
          this.errorMessage = `No payment found for Quote ID ${quoteId}`;
        }
        this.quoteIdInput = '';
      },
      error: (err) => {
        this.errorMessage = err.error?.message || `Failed to fetch payment for Quote ID ${quoteId}`;
        this.quoteIdInput = '';
      }
    });
  }

  makePayment(payment: PaymentDTO): void {
    if (!payment.id || !payment.quoteId) {
      this.errorMessage = 'Invalid payment ID or Quote ID';
      return;
    }

    const updatedPayment: PaymentDTO = {
      id: payment.id,
      quoteId: payment.quoteId,
      paymentStatus: true,
      paidOn: new Date().toISOString()
    };

    console.log('Updating payment:', updatedPayment);
    this.paymentService.updatePayment(payment.id, updatedPayment).subscribe({
      next: (result) => {
        this.successMessage = `Payment ${payment.id} marked as paid`;
        this.errorMessage = null;
        this.payments = this.payments.map(p => p.id === payment.id ? result : p);
      },
      error: (err) => {
        this.errorMessage = err.error?.message || 'Failed to update payment';
        this.successMessage = null;
        console.error('Update payment error:', err);
      }
    });
  }
}