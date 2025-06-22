/*
 * ProposalRepository.java
 * 
 * Repository interface extending JpaRepository to manage Proposal entities
 * with Long IDs. Provides a method to find all Proposals by the associated
 * user's ID, returning a list of proposals.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */
package com.hexaware.automobile.repositories;

import com.hexaware.automobile.entities.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProposalRepository extends JpaRepository<Proposal, Long> {
    List<Proposal> findByUserId(Long userId);
}
