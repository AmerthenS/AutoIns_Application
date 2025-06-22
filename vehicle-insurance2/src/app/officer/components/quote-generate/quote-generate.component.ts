import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { QuoteService } from '../../../core/services/quote.service';
import { ProposalService } from '../../../core/services/proposal.service';
import { ProposalDTO } from '../../../core/models/proposal.dto';

@Component({
  selector: 'app-quote-generate',
  templateUrl: './quote-generate.component.html',
  styleUrls: ['./quote-generate.component.css']
})
export class QuoteGenerateComponent implements OnInit {
  quoteForm: FormGroup;
  proposals: ProposalDTO[] = [];
  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private quoteService: QuoteService,
    private proposalService: ProposalService
  ) {
    this.quoteForm = this.fb.group({
      proposalId: ['', Validators.required],
      amount: ['', [Validators.required, Validators.min(0)]]
    });
  }

  ngOnInit(): void {
    this.proposalService.getAllProposals().subscribe({
      next: (proposals) => this.proposals = proposals,
      error: (err) => this.errorMessage = err.error || 'Failed to load proposals'
    });
  }

  onSubmit(): void {
    if (this.quoteForm.valid) {
      this.quoteService.createQuote(this.quoteForm.value).subscribe({
        next: () => {
          this.successMessage = 'Quote generated successfully';
          this.errorMessage = null;
          this.quoteForm.reset();
        },
        error: (err) => this.errorMessage = err.error || 'Failed to generate quote'
      });
    }
  }
}