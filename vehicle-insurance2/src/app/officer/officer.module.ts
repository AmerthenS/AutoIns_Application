import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { OfficerRoutingModule } from './officer-routing.module';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ProfileComponent } from './components/profile/profile.component';
import { ProposalListComponent } from './components/proposal-list/proposal-list.component';
import { DocumentVerifyComponent } from './components/document-verify/document-verify.component';
import { QuoteGenerateComponent } from './components/quote-generate/quote-generate.component';
import { PaymentViewComponent } from './components/payment-view/payment-view.component';
import { PolicyActivateComponent } from './components/policy-activate/policy-activate.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [
    DashboardComponent,
    ProfileComponent,
    ProposalListComponent,
    DocumentVerifyComponent,
    QuoteGenerateComponent,
    PaymentViewComponent,
    PolicyActivateComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    OfficerRoutingModule,
    SharedModule
  ]
})
export class OfficerModule { }