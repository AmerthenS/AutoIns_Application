/*
 * UserService.java
 * 
 * Interface for user-related operations including registration, login, 
 * retrieval, update, and deletion of users.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */
package com.hexaware.automobile.services;

import com.hexaware.automobile.dtos.UserDTO;
import com.hexaware.automobile.dtos.LoginResponseDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO registerUser(UserDTO userDTO) throws Exception;
    LoginResponseDTO loginUser(String email, String password) throws Exception;
    UserDTO getUserById(Long id) throws Exception;
    List<UserDTO> getAllUsers();
    void logoutUser(String token);
    Optional<UserDTO> updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
}