import { Component, OnInit } from '@angular/core';
import { PolicyService } from '../../../core/services/policy.service';
import { PolicyDTO } from '../../../core/models/policy.dto';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-policy-activate',
  templateUrl: './policy-activate.component.html',
  styleUrls: ['./policy-activate.component.css']
})
export class PolicyActivateComponent implements OnInit {
  policies: PolicyDTO[] = [];
  errorMessage: string | null = null;
  successMessage: string | null = null;
  newPolicy: PolicyDTO = {
    proposalId: undefined,
    policyNumber: '',
    startDate: new Date().toISOString().split('T')[0], // Today
    endDate: new Date(new Date().setFullYear(new Date().getFullYear() + 1)).toISOString().split('T')[0], // +1 year
    pdfUrl: '',
    status: 'ACTIVE'
  };

  constructor(private policyService: PolicyService, private authService: AuthService) {
    console.log('JWT Token:', this.authService.getToken()); // Debug
  }

  ngOnInit(): void {
    this.loadPolicies();
  }

  loadPolicies(): void {
    this.policyService.getAllPolicies().subscribe({
      next: (policies) => this.policies = policies,
      error: (err) => this.errorMessage = err.error?.message || 'Failed to load policies'
    });
  }

  createPolicy(): void {
    const policy: PolicyDTO = {
      proposalId: this.newPolicy.proposalId,
      policyNumber: this.newPolicy.policyNumber,
      startDate: new Date(this.newPolicy.startDate).toISOString(),
      endDate: new Date(this.newPolicy.endDate).toISOString(),
      pdfUrl: this.newPolicy.pdfUrl || undefined,
      status: 'ACTIVE'
    };

    console.log('Creating policy:', policy); // Debug
    this.policyService.createPolicy(policy).subscribe({
      next: (createdPolicy) => {
        this.successMessage = `Policy ${createdPolicy.policyNumber} created successfully`;
        this.errorMessage = null;
        this.policies = [...this.policies, createdPolicy];
        this.resetForm();
      },
      error: (err) => {
        this.errorMessage = err.error?.message || 'Failed to create policy';
        this.successMessage = null;
        console.error('Policy creation error:', err); // Line ~57
      }
    });
  }

  resetForm(): void {
    this.newPolicy = {
      proposalId: undefined,
      policyNumber: '',
      startDate: new Date().toISOString().split('T')[0],
      endDate: new Date(new Date().setFullYear(new Date().getFullYear() + 1)).toISOString().split('T')[0],
      pdfUrl: '',
      status: 'ACTIVE'
    };
  }
}