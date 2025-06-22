/*
 * OfficerService.java
 * 
 * Interface for officer-related operations including registration, retrieval,
 * update, deletion, login, and email existence check.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */
package com.hexaware.automobile.services;

import com.hexaware.automobile.dtos.OfficerDTO;

import java.util.List;

public interface OfficerService {
    OfficerDTO registerOfficer(OfficerDTO dto);
    OfficerDTO getOfficerById(Long officerId);
    List<OfficerDTO> getAllOfficers();
    OfficerDTO updateOfficer(Long officerId, OfficerDTO dto);
    void deleteOfficer(Long officerId);
    OfficerDTO loginOfficer(String email, String password);
    boolean existsByEmail(String email);
}