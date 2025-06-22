import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { QuoteDTO } from '../models/quote.dto';

@Injectable({
  providedIn: 'root'
})
export class QuoteService {
  private apiUrl = `${environment.apiUrl}/quotes`;

  constructor(private http: HttpClient) {}

  createQuote(quote: QuoteDTO): Observable<QuoteDTO> {
    return this.http.post<QuoteDTO>(this.apiUrl, quote);
  }

  getQuoteById(id: number): Observable<QuoteDTO> {
    return this.http.get<QuoteDTO>(`${this.apiUrl}/${id}`);
  }

  getQuoteByProposalId(proposalId: number): Observable<QuoteDTO> {
    return this.http.get<QuoteDTO>(`${this.apiUrl}/proposal/${proposalId}`);
  }

  updateQuote(id: number, quote: QuoteDTO): Observable<QuoteDTO> {
    return this.http.put<QuoteDTO>(`${this.apiUrl}/${id}`, quote);
  }

  getAllQuotes(): Observable<QuoteDTO[]> {
    return this.http.get<QuoteDTO[]>(this.apiUrl);
  }
}