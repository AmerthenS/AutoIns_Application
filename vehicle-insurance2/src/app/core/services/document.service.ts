import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { DocumentDTO } from '../models/document.dto';

@Injectable({
  providedIn: 'root'
})
export class DocumentService {
  private apiUrl = `${environment.apiUrl}/documents`;

  constructor(private http: HttpClient) {}

  uploadDocument(document: DocumentDTO): Observable<DocumentDTO> {
    return this.http.post<DocumentDTO>(this.apiUrl, document);
  }

  getDocumentsByProposalId(proposalId: number): Observable<DocumentDTO[]> {
    return this.http.get<DocumentDTO[]>(`${this.apiUrl}/proposal/${proposalId}`);
  }

  verifyDocument(id: number, verified: boolean, remarks?: string): Observable<DocumentDTO> {
    return this.http.put<DocumentDTO>(`${this.apiUrl}/${id}/verify`, null, {
      params: { verified: verified.toString(), remarks: remarks || '' }
    });
  }

  getDocumentsByUserId(): Observable<DocumentDTO[]> {
    return this.http.get<DocumentDTO[]>(`${this.apiUrl}/my`);
  }
}