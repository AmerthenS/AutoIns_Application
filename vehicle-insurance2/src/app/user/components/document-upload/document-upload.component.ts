import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DocumentService } from '../../../core/services/document.service';
import { ProposalService } from '../../../core/services/proposal.service';
import { AuthService } from '../../../core/services/auth.service';
import { ProposalDTO } from '../../../core/models/proposal.dto';
import { DocumentDTO } from '../../../core/models/document.dto';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-document-upload',
  templateUrl: './document-upload.component.html',
  styleUrls: ['./document-upload.component.css']
})
export class DocumentUploadComponent implements OnInit {
  documentForm: FormGroup;
  proposals: ProposalDTO[] = [];
  documents: DocumentDTO[] = [];
  docTypes = ['RC Book', 'License', 'Insurance'];
  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private documentService: DocumentService,
    private proposalService: ProposalService,
    private authService: AuthService
  ) {
    this.documentForm = this.fb.group({
      proposalId: ['', Validators.required],
      docType: ['', [Validators.required, Validators.maxLength(50)]],
      fileUrl: ['', [Validators.required, Validators.pattern(/^(https?:\/\/)?([\da-z.-]+)\.([a-z.]{2,6})([/\w .-]*)*\/?$/)]]
    });
  }

  ngOnInit(): void {
    const userId = this.authService.getUserId();
    if (userId) {
      forkJoin({
        proposals: this.proposalService.getProposalsByUserId(userId),
        documents: this.documentService.getDocumentsByUserId()
      }).subscribe({
        next: ({ proposals, documents }) => {
          this.proposals = proposals;
          this.documents = documents;
        },
        error: (err) => this.errorMessage = err.error?.message || 'Failed to load data'
      });
    } else {
      this.errorMessage = 'User not authenticated';
    }
  }

  onSubmit(): void {
    if (this.documentForm.valid) {
      this.documentService.uploadDocument(this.documentForm.value).subscribe({
        next: (document) => {
          this.successMessage = 'Document uploaded successfully';
          this.errorMessage = null;
          this.documents.push(document);
          this.documentForm.reset();
        },
        error: (err) => this.errorMessage = err.error?.message || 'Failed to upload document'
      });
    }
  }
}