import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';
import { OfficerService } from '../../../core/services/officer.service';
import { OfficerDTO } from '../../../core/models/officer.dto';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  profileForm: FormGroup;
  errorMessage: string | null = null;
  successMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private officerService: OfficerService
  ) {
    this.profileForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
      email: ['', [Validators.required, Validators.email, Validators.maxLength(100)]]
    });
  }

  ngOnInit(): void {
    const officerId = this.authService.getUserId();
    if (officerId) {
      this.officerService.getOfficerById(officerId).subscribe({
        next: (officer) => {
          this.profileForm.patchValue(officer);
        },
        error: (err) => this.errorMessage = err.error || 'Failed to load profile'
      });
    }
  }

  onSubmit(): void {
    if (this.profileForm.valid) {
      const officerId = this.authService.getUserId();
      if (officerId) {
        this.officerService.updateOfficer(officerId, this.profileForm.value).subscribe({
          next: () => {
            this.successMessage = 'Profile updated successfully';
            this.errorMessage = null;
          },
          error: (err) => this.errorMessage = err.error || 'Failed to update profile'
        });
      }
    }
  }
}