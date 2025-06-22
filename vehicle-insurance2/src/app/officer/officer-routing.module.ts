import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from '../core/guards/auth.guard';
import { RoleGuard } from '../core/guards/role.guard';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ProfileComponent } from './components/profile/profile.component';
import { ProposalListComponent } from './components/proposal-list/proposal-list.component';
import { DocumentVerifyComponent } from './components/document-verify/document-verify.component';
import { QuoteGenerateComponent } from './components/quote-generate/quote-generate.component';
import { PaymentViewComponent } from './components/payment-view/payment-view.component';
import { PolicyActivateComponent } from './components/policy-activate/policy-activate.component';

const routes: Routes = [
  {
    path: '',
    canActivate: [AuthGuard, RoleGuard],
    data: { role: 'ROLE_OFFICER' },
    children: [
      { path: 'dashboard', component: DashboardComponent },
      { path: 'profile', component: ProfileComponent },
      { path: 'proposals', component: ProposalListComponent },
      { path: 'documents', component: DocumentVerifyComponent },
      { path: 'quotes', component: QuoteGenerateComponent },
      { path: 'payments', component: PaymentViewComponent },
      { path: 'policies', component: PolicyActivateComponent },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OfficerRoutingModule { }