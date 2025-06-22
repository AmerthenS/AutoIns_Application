import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  ngOnInit(): void {}

  onSubmit(): void {
    if (this.loginForm.valid) {
      const { email, password } = this.loginForm.value;
      this.authService.login(email, password).subscribe({
        next: (response: { token: string; message: string }) => {
          const role = this.authService.getRole();
          if (role === 'ROLE_USER') {
            this.router.navigate(['/user/dashboard']);
          } else if (role === 'ROLE_OFFICER') {
            this.router.navigate(['/officer/dashboard']);
          } else {
            this.errorMessage = 'Invalid user role';
          }
        },
        error: (err: any) => {
          this.errorMessage = err.error?.message || 'Invalid email or password';
        }
      });
    }
  }
}