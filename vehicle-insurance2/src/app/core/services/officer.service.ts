import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { OfficerDTO } from '../models/officer.dto';

@Injectable({
  providedIn: 'root'
})
export class OfficerService {
  private apiUrl = `${environment.apiUrl}/officers`;

  constructor(private http: HttpClient) {}

  register(officer: OfficerDTO): Observable<OfficerDTO> {
    return this.http.post<OfficerDTO>(`${this.apiUrl}/register`, officer);
  }

  getOfficerById(id: number): Observable<OfficerDTO> {
    return this.http.get<OfficerDTO>(`${this.apiUrl}/${id}`);
  }

  updateOfficer(id: number, officer: OfficerDTO): Observable<OfficerDTO> {
    return this.http.put<OfficerDTO>(`${this.apiUrl}/${id}`, officer);
  }
}