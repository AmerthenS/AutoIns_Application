import { Component } from '@angular/core';
import { QuoteService } from '../../../core/services/quote.service';
import { QuoteDTO } from '../../../core/models/quote.dto';

@Component({
  selector: 'app-quote-view',
  templateUrl: './quote-view.component.html',
  styleUrls: ['./quote-view.component.css']
})
export class QuoteViewComponent {
  proposalId: number | null = null;
  quote: QuoteDTO | null = null;
  errorMessage: string | null = null;

  constructor(private quoteService: QuoteService) {}

  onSubmit(): void {
    if (this.proposalId) {
      this.quoteService.getQuoteByProposalId(this.proposalId).subscribe({
        next: (quote) => {
          this.quote = quote;
          this.errorMessage = null;
        },
        error: (err) => {
          this.quote = null;
          this.errorMessage = err.error?.message || 'Failed to load quote. Please check the Proposal ID.';
          console.error(err);
        }
      });
    } else {
      this.errorMessage = 'Please enter a valid Proposal ID.';
    }
  }
}