import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { UserRoutingModule } from './user-routing.module';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ProfileComponent } from './components/profile/profile.component';
import { ProposalFormComponent } from './components/proposal-form/proposal-form.component';
import { DocumentUploadComponent } from './components/document-upload/document-upload.component';
import { QuoteViewComponent } from './components/quote-view/quote-view.component';
import { PaymentComponent } from './components/payment/payment.component';
import { PolicyViewComponent } from './components/policy-view/policy-view.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [
    DashboardComponent,
    ProfileComponent,
    ProposalFormComponent,
    DocumentUploadComponent,
    QuoteViewComponent,
    PaymentComponent,
    PolicyViewComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    UserRoutingModule,
    SharedModule
  ]
})
export class UserModule { }