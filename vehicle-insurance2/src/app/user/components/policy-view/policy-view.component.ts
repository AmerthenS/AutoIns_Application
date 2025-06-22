import { Component, OnInit } from '@angular/core';
import { PolicyService } from '../../../core/services/policy.service';
import { PolicyDTO } from '../../../core/models/policy.dto';

@Component({
  selector: 'app-policy-view',
  templateUrl: './policy-view.component.html',
  styleUrls: ['./policy-view.component.css']
})
export class PolicyViewComponent implements OnInit {
  policies: PolicyDTO[] = [];
  errorMessage: string | null = null;

  constructor(private policyService: PolicyService) {}

  ngOnInit(): void {
    this.policyService.getMyPolicies().subscribe({
      next: (policies) => this.policies = policies,
      error: (err) => this.errorMessage = err.error || 'Failed to load policies'
    });
  }
}