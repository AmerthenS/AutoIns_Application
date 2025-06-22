import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ProposalDTO } from '../models/proposal.dto';

@Injectable({
  providedIn: 'root'
})
export class ProposalService {
  private apiUrl = `${environment.apiUrl}/proposals`;

  constructor(private http: HttpClient) {}

  createProposal(proposal: ProposalDTO): Observable<ProposalDTO> {
    return this.http.post<ProposalDTO>(this.apiUrl, proposal);
  }

  getProposalById(id: number): Observable<ProposalDTO> {
    return this.http.get<ProposalDTO>(`${this.apiUrl}/${id}`);
  }

  getProposalsByUserId(userId: number): Observable<ProposalDTO[]> {
    return this.http.get<ProposalDTO[]>(`${this.apiUrl}/user/${userId}`);
  }

  updateProposal(id: number, proposal: ProposalDTO): Observable<ProposalDTO> {
    return this.http.put<ProposalDTO>(`${this.apiUrl}/${id}`, proposal);
  }

  getAllProposals(): Observable<ProposalDTO[]> {
    return this.http.get<ProposalDTO[]>(this.apiUrl);
  }
}