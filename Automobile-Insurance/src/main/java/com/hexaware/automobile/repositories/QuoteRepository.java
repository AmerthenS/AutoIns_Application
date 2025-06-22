/*
 * QuoteRepository.java
 * 
 * Repository interface extending JpaRepository to manage Quote entities
 * with Long IDs. Provides methods to find a Quote by the associated proposal's ID,
 * either by the proposal ID or by the Proposal entity itself.
 * 
 * Author: Amerthen
 * Date: 2025-06-02
 */
package com.hexaware.automobile.repositories;

import com.hexaware.automobile.entities.Proposal;
import com.hexaware.automobile.entities.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

    Optional<Quote> findByProposalId(Long proposalId); 
	Object findByProposalId(Proposal proposal);
	@Query("SELECT q FROM Quote q JOIN q.proposal p WHERE p.user.id = :userId")
    List<Quote> findByProposalUserId(@Param("userId") Long userId);

}
