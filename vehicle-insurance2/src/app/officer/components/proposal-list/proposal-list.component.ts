import { Component, OnInit } from '@angular/core';
import { ProposalService } from '../../../core/services/proposal.service';
import { ProposalDTO } from '../../../core/models/proposal.dto';
import { ProposalStatus } from '../../../core/models/proposal-type.dto';

@Component({
  selector: 'app-proposal-list',
  templateUrl: './proposal-list.component.html',
  styleUrls: ['./proposal-list.component.css']
})
export class ProposalListComponent implements OnInit {
  proposals: ProposalDTO[] = [];
  errorMessage: string | null = null;
  successMessage: string | null = null;
  // Define status values for the dropdown
  proposalStatuses: ProposalStatus[] = [
    'SUBMITTED',
    'DOCUMENTS_REQUESTED',
    'QUOTE_GENERATED',
    'PAYMENT_PENDING',
    'ACTIVE',
    'REJECTED',
    'EXPIRED'
  ];

  constructor(private proposalService: ProposalService) {}

  ngOnInit(): void {
    this.proposalService.getAllProposals().subscribe({
      next: (proposals) => this.proposals = proposals,
      error: (err) => this.errorMessage = err.error || 'Failed to load proposals'
    });
  }

  updateStatus(proposalId: number, status: ProposalStatus): void {
    const proposal = this.proposals.find(p => p.id === proposalId);
    if (proposal) {
      this.proposalService.updateProposal(proposalId, { ...proposal, status }).subscribe({
        next: () => {
          this.successMessage = `Proposal #${proposalId} status updated to ${status}`;
          this.errorMessage = null;
          this.proposals = this.proposals.map(p => p.id === proposalId ? { ...p, status } : p);
        },
        error: (err) => this.errorMessage = err.error || 'Failed to update proposal status'
      });
    }
  }
}