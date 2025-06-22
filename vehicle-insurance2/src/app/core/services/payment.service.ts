import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { PaymentDTO } from '../models/payment.dto';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  private apiUrl = `${environment.apiUrl}/payments`;

  constructor(private http: HttpClient) {}

  createPayment(payment: PaymentDTO): Observable<PaymentDTO> {
    return this.http.post<PaymentDTO>(this.apiUrl, payment);
  }

  getPaymentById(id: number): Observable<PaymentDTO> {
    return this.http.get<PaymentDTO>(`${this.apiUrl}/${id}`);
  }

  updatePayment(id: number, payment: PaymentDTO): Observable<PaymentDTO> {
    return this.http.put<PaymentDTO>(`${this.apiUrl}/${id}`, payment);
  }

  getAllPayments(): Observable<PaymentDTO[]> {
    return this.http.get<PaymentDTO[]>(this.apiUrl);
  }

  getPaymentByQuoteId(quoteId: number): Observable<PaymentDTO | null> {
    return this.http.get<PaymentDTO>(`${this.apiUrl}/byquote/${quoteId}`);
  }
}