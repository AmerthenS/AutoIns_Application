import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DocumentService } from '../../../core/services/document.service';
import { DocumentDTO } from '../../../core/models/document.dto';

@Component({
  selector: 'app-document-verify',
  templateUrl: './document-verify.component.html',
  styleUrls: ['./document-verify.component.css']
})
export class DocumentVerifyComponent implements OnInit {
  documents: DocumentDTO[] = [];
  verifyForm: FormGroup;
  selectedDocument: DocumentDTO | null = null;
  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(
    private documentService: DocumentService,
    private fb: FormBuilder
  ) {
    this.verifyForm = this.fb.group({
      verified: [false, Validators.required],
      remarks: ['', Validators.maxLength(255)]
    });
  }

  ngOnInit(): void {
    this.documentService.getDocumentsByProposalId(0).subscribe({
      next: (documents) => this.documents = documents,
      error: (err) => this.errorMessage = err.error || 'Failed to load documents'
    });
  }

  selectDocument(document: DocumentDTO): void {
    this.selectedDocument = document;
    this.verifyForm.patchValue({
      verified: document.verified || false,
      remarks: document.remarks || ''
    });
  }

  onSubmit(): void {
    if (this.verifyForm.valid && this.selectedDocument) {
      const { verified, remarks } = this.verifyForm.value;
      this.documentService.verifyDocument(this.selectedDocument.id!, verified, remarks).subscribe({
        next: () => {
          this.successMessage = `Document #${this.selectedDocument!.id} verified successfully`;
          this.errorMessage = null;
          this.documents = this.documents.map(d =>
            d.id === this.selectedDocument!.id ? { ...d, verified, remarks } : d
          );
          this.selectedDocument = null;
          this.verifyForm.reset();
        },
        error: (err) => this.errorMessage = err.error || 'Failed to verify document'
      });
    }
  }
}