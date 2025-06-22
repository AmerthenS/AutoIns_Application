import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProposalService } from '../../../core/services/proposal.service';
import { AuthService } from '../../../core/services/auth.service';
import { ProposalDTO } from '../../../core/models/proposal.dto';

@Component({
  selector: 'app-proposal-form',
  templateUrl: './proposal-form.component.html',
  styleUrls: ['./proposal-form.component.css']
})
export class ProposalFormComponent implements OnInit {
  proposalForm: FormGroup;
  vehicleTypes: ['Car', 'Bike', 'Truck'] = ['Car', 'Bike', 'Truck'];
  proposals: ProposalDTO[] = [];
  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private proposalService: ProposalService,
    private authService: AuthService
  ) {
    this.proposalForm = this.fb.group({
      vehicleType: ['', [Validators.required, Validators.pattern('^(Car|Bike|Truck)$')]]
    });
  }

  ngOnInit(): void {
    const userId = this.authService.getUserId();
    if (userId) {
      this.proposalService.getProposalsByUserId(userId).subscribe({
        next: (proposals) => {
          this.proposals = proposals;
        },
        error: (err) => {
          this.errorMessage = err.error?.message || 'Failed to load proposals';
        }
      });
    } else {
      this.errorMessage = 'User not authenticated';
    }
  }

  onSubmit(): void {
    if (this.proposalForm.valid) {
      const userId = this.authService.getUserId();
      if (userId) {
        const proposal: ProposalDTO = {
          vehicleType: this.proposalForm.value.vehicleType,
          userId
        };
        this.proposalService.createProposal(proposal).subscribe({
          next: (response: ProposalDTO) => {
            this.successMessage = 'Proposal submitted successfully';
            this.errorMessage = null;
            this.proposals.push(response); // Add new proposal to table
            this.proposalForm.reset();
          },
          error: (err: any) => {
            this.errorMessage = err.error?.message || 'Failed to submit proposal';
          }
        });
      } else {
        this.errorMessage = 'Please log in to submit a proposal';
        this.successMessage = null;
      }
    }
  }
}