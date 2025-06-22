export interface QuoteDTO {
  id?: number;
  proposalId?: number;
  amount: number;
  generatedOn?: string; // ISO date-time string
}