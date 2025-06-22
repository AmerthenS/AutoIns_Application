export interface UserDTO {
  id?: number;
  name: string;
  email: string;
  password: string;
  address?: string;
  aadhaarNumber?: string;
  panNumber?: string;
  dob?: string; 
  role?: 'ROLE_USER' | 'ROLE_OFFICER';
}