import { Component, OnInit } from '@angular/core';
import { PaymentService } from '../../../core/services/payment.service';
import { QuoteService } from '../../../core/services/quote.service';
import { ProposalService } from '../../../core/services/proposal.service';
import { PaymentDTO } from '../../../core/models/payment.dto';
import { QuoteDTO } from '../../../core/models/quote.dto';
import { ProposalDTO } from '../../../core/models/proposal.dto';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-payment-view',
  templateUrl: './payment-view.component.html',
  styleUrls: ['./payment-view.component.css']
})
export class PaymentViewComponent implements OnInit {
  payments: PaymentDTO[] = [];
  quotes: QuoteDTO[] = [];
  proposals: ProposalDTO[] = [];
  errorMessage: string | null = null;

  constructor(
    private paymentService: PaymentService,
    private quoteService: QuoteService,
    private proposalService: ProposalService
  ) {}

  ngOnInit(): void {
    forkJoin({
      proposals: this.proposalService.getAllProposals(),
      quotes: this.quoteService.getAllQuotes(),
      payments: this.paymentService.getAllPayments()
    }).subscribe({
      next: ({ proposals, quotes, payments }) => {
        this.proposals = proposals;
        this.quotes = quotes;
        this.payments = payments;
      },
      error: (err) => this.errorMessage = err.error?.message || 'Failed to load data'
    });
  }

  getQuoteForPayment(payment: PaymentDTO): QuoteDTO | undefined {
    return this.quotes.find(quote => quote.id === payment.quoteId);
  }

  getProposalForQuote(quote: QuoteDTO | undefined): ProposalDTO | undefined {
    if (!quote) return undefined;
    return this.proposals.find(proposal => proposal.id === quote.proposalId);
  }
}