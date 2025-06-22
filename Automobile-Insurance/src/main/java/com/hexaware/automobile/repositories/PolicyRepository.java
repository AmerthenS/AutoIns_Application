/*
 * PolicyRepository.java
 * 
 * Repository interface extending JpaRepository to manage Policy entities
 * with Long IDs. Provides methods to find a Policy by proposal ID (optional)
 * and to find all Policies by the user ID associated with the proposal.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */
package com.hexaware.automobile.repositories;

import com.hexaware.automobile.entities.Policy;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
	Optional<Policy> findByProposalId(Long proposalId);
    List<Policy> findByProposalUserId(Long userId);
    

}
