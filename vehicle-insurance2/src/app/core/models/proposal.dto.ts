export interface ProposalDTO {
  id?: number;
  userId?: number;
  vehicleType: string;
  status?: string
  createdAt?: string; // ISO date-time string
  updatedAt?: string; // ISO date-time string
}