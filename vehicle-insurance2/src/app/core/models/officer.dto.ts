export interface OfficerDTO {
  id?: number;
  name: string;
  email: string;
  password?: string;
  role?: 'ROLE_USER' | 'ROLE_OFFICER';
}