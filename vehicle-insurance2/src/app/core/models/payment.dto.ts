export interface PaymentDTO {
  id?: number;
  quoteId: number;
  paymentStatus: boolean;
  paidOn?: string; 
}