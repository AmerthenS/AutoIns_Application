/*
 * PolicyService.java
 * 
 * Service interface defining methods to manage insurance policies.
 * Supports creating, retrieving by ID or proposal ID, updating, listing all, deleting policies,
 * and retrieving policies by user ID.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */
package com.hexaware.automobile.services;

import com.hexaware.automobile.dtos.PolicyDTO;

import java.util.List;

public interface PolicyService {
    PolicyDTO createPolicy(PolicyDTO policyDTO);
    PolicyDTO getPolicyById(Long id);
    PolicyDTO getPolicyByProposalId(Long proposalId);
    List<PolicyDTO> getAllPolicies();
    PolicyDTO updatePolicy(Long id, PolicyDTO policyDTO);
    void deletePolicy(Long id);
    List<PolicyDTO> getPoliciesByUserId(Long userId);
}

