import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { PolicyDTO } from '../models/policy.dto';

@Injectable({
  providedIn: 'root'
})
export class PolicyService {
  private apiUrl = `${environment.apiUrl}/policies`;

  constructor(private http: HttpClient) {}

  createPolicy(policy: PolicyDTO): Observable<PolicyDTO> {
    return this.http.post<PolicyDTO>(`${this.apiUrl}/policies`, policy);
  }

  getMyPolicies(): Observable<PolicyDTO[]> {
    return this.http.get<PolicyDTO[]>(`${this.apiUrl}/my`);
  }

  getAllPolicies(): Observable<PolicyDTO[]> {
    return this.http.get<PolicyDTO[]>(this.apiUrl);
  }

  updatePolicy(id: number, policy: PolicyDTO): Observable<PolicyDTO> {
    return this.http.put<PolicyDTO>(`${this.apiUrl}/${id}`, policy);
  }
}