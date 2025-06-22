import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ProfileComponent } from './components/profile/profile.component';
import { ProposalFormComponent } from './components/proposal-form/proposal-form.component';
import { DocumentUploadComponent } from './components/document-upload/document-upload.component';
import { QuoteViewComponent } from './components/quote-view/quote-view.component';
import { PaymentComponent } from './components/payment/payment.component';
import { PolicyViewComponent } from './components/policy-view/policy-view.component';

const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'proposals', component: ProposalFormComponent },
  { path: 'documents', component: DocumentUploadComponent },
  { path: 'quotes', component: QuoteViewComponent },
  { path: 'payments', component: PaymentComponent },
  { path: 'policies', component: PolicyViewComponent },
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }