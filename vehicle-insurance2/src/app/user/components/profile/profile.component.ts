import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';
import { UserService } from '../../../core/services/user.service';
import { UserDTO } from '../../../core/models/user.dto';

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
    private userService: UserService
  ) {
    this.profileForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
      email: ['', [Validators.required, Validators.email, Validators.maxLength(100)]],
      password: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(255)]],
      address: ['', [Validators.maxLength(255)]],
      aadhaarNumber: ['', [Validators.pattern(/^\d{12}$/)]],
      panNumber: ['', [Validators.pattern(/^[A-Z]{5}\d{4}[A-Z]{1}$/)]],
      dob: [''],
      role: ['ROLE_USER', Validators.required]
    });
  }

  ngOnInit(): void {
    const userId = this.authService.getUserId();
    if (userId) {
      this.userService.getUserById(userId).subscribe({
        next: (user) => {
          this.profileForm.patchValue({
            name: user.name,
            email: user.email,
            address: user.address,
            aadhaarNumber: user.aadhaarNumber,
            panNumber: user.panNumber,
            dob: user.dob,
            role: user.role
            // Password not populated for security
          });
        },
        error: (err) => this.errorMessage = err.error?.message || 'Failed to load profile'
      });
    }
  }

  onSubmit(): void {
    if (this.profileForm.invalid) {
      this.errorMessage = 'Please fill out all required fields correctly.';
      return;
    }

    const userId = this.authService.getUserId();
    if (userId) {
      const userData = this.profileForm.value;
      console.log('Sending payload:', userData); // Debug payload
      this.userService.updateUser(userId, userData).subscribe({
        next: () => {
          this.successMessage = 'Profile updated successfully';
          this.errorMessage = null;
        },
        error: (err) => {
          this.errorMessage = err.error?.message || err.error || 'Failed to update profile';
          console.error('Update error:', err); // Debug error
        }
      });
    }
  }
}