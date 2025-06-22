export interface PolicyDTO {
  id?: number;
  proposalId?: number;
  policyNumber: string;
  startDate: string; 
  endDate: string; 
  pdfUrl?: string;
  status?: 'ACTIVE' | 'EXPIRED' | 'CANCELLED';
}