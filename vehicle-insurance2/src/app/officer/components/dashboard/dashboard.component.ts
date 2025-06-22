import { Component, OnInit } from '@angular/core';
import { OfficerService } from '../../../core/services/officer.service';
import { ProposalService } from '../../../core/services/proposal.service';
import { QuoteService } from '../../../core/services/quote.service';
import { PaymentService } from '../../../core/services/payment.service';
import { PolicyService } from '../../../core/services/policy.service';
import { AuthService } from '../../../core/services/auth.service';
import { OfficerDTO } from '../../../core/models/officer.dto';
import { ProposalDTO } from '../../../core/models/proposal.dto';
import { QuoteDTO } from '../../../core/models/quote.dto';
import { PaymentDTO } from '../../../core/models/payment.dto';
import { PolicyDTO } from '../../../core/models/policy.dto';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  officer: OfficerDTO | null = null;
  proposals: ProposalDTO[] = [];
  quotes: QuoteDTO[] = [];
  payments: PaymentDTO[] = [];
  policies: PolicyDTO[] = [];
  errorMessage: string | null = null;

  constructor(
    private authService: AuthService,
    private officerService: OfficerService,
    private proposalService: ProposalService,
    private quoteService: QuoteService,
    private paymentService: PaymentService,
    private policyService: PolicyService
  ) {}

  ngOnInit(): void {
    const officerId = this.authService.getUserId();
    if (officerId) {
      this.officerService.getOfficerById(officerId).subscribe({
        next: (officer) => this.officer = officer,
        error: (err) => this.errorMessage = err.error || 'Failed to load officer data'
      });
      this.proposalService.getAllProposals().subscribe({
        next: (proposals) => this.proposals = proposals,
        error: (err) => this.errorMessage = err.error || 'Failed to load proposals'
      });
      this.quoteService.getAllQuotes().subscribe({
        next: (quotes) => this.quotes = quotes,
        error: (err) => this.errorMessage = err.error || 'Failed to load quotes'
      });
      this.paymentService.getAllPayments().subscribe({
        next: (payments) => this.payments = payments,
        error: (err) => this.errorMessage = err.error || 'Failed to load payments'
      });
      this.policyService.getAllPolicies().subscribe({
        next: (policies) => this.policies = policies,
        error: (err) => this.errorMessage = err.error || 'Failed to load policies'
      });
    }
  }
}