export interface DocumentDTO {
  id?: number;
  proposalId?: number;
  docType: string;
  fileUrl: string;
  verified?: boolean;
  remarks?: string;
}