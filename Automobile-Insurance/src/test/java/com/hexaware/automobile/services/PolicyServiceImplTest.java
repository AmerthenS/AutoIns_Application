package com.hexaware.automobile.services;

import com.hexaware.automobile.dtos.PolicyDTO;
import com.hexaware.automobile.entities.Policy;
import com.hexaware.automobile.entities.Proposal;
import com.hexaware.automobile.exceptions.ResourceNotFoundException;
import com.hexaware.automobile.repositories.PolicyRepository;
import com.hexaware.automobile.repositories.ProposalRepository;
import com.hexaware.automobile.services.impl.PolicyServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PolicyServiceImplTest {

    @Mock
    private PolicyRepository policyRepository;

    @Mock
    private ProposalRepository proposalRepository;

    @InjectMocks
    private PolicyServiceImpl policyService;

    private Policy policy;
    private PolicyDTO policyDTO;
    private Proposal proposal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        proposal = new Proposal();
        proposal.setId(10L);

        policy = new Policy();
        policy.setId(1L);
        policy.setProposal(proposal);
        policy.setPolicyNumber("PN123456");
        policy.setStartDate(LocalDate.now());
        policy.setEndDate(LocalDate.now().plusYears(1));
        policy.setPdfUrl("http://example.com/policy.pdf");
        policy.setStatus(Policy.Status.ACTIVE);

        policyDTO = new PolicyDTO();
        policyDTO.setId(1L);
        policyDTO.setProposalId(proposal.getId());
        policyDTO.setPolicyNumber("PN123456");
        policyDTO.setStartDate(policy.getStartDate());
        policyDTO.setEndDate(policy.getEndDate());
        policyDTO.setPdfUrl(policy.getPdfUrl());
        policyDTO.setStatus("ACTIVE");
    }

    @Test
    void testCreatePolicy_Success() {
        when(proposalRepository.findById(policyDTO.getProposalId())).thenReturn(Optional.of(proposal));
        when(policyRepository.save(any(Policy.class))).thenReturn(policy);

        PolicyDTO created = policyService.createPolicy(policyDTO);

        assertNotNull(created);
        assertEquals(policyDTO.getPolicyNumber(), created.getPolicyNumber());
        verify(proposalRepository, times(1)).findById(policyDTO.getProposalId());
        verify(policyRepository, times(1)).save(any(Policy.class));
    }

    @Test
    void testCreatePolicy_ProposalNotFound() {
        when(proposalRepository.findById(policyDTO.getProposalId())).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> policyService.createPolicy(policyDTO));
        assertEquals("Proposal not found with id " + policyDTO.getProposalId(), ex.getMessage());
    }

    @Test
    void testGetPolicyById_Success() {
        when(policyRepository.findById(1L)).thenReturn(Optional.of(policy));

        PolicyDTO dto = policyService.getPolicyById(1L);

        assertNotNull(dto);
        assertEquals(policy.getPolicyNumber(), dto.getPolicyNumber());
    }

    @Test
    void testGetPolicyById_NotFound() {
        when(policyRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> policyService.getPolicyById(1L));
        assertEquals("Policy not found with id 1", ex.getMessage());
    }

    @Test
    void testGetAllPolicies() {
        List<Policy> policies = List.of(policy);
        when(policyRepository.findAll()).thenReturn(policies);

        List<PolicyDTO> result = policyService.getAllPolicies();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(policy.getPolicyNumber(), result.get(0).getPolicyNumber());
    }

    @Test
    void testGetPoliciesByUserId() {
        List<Policy> policies = List.of(policy);
        when(policyRepository.findByProposalUserId(10L)).thenReturn(policies);

        List<PolicyDTO> result = policyService.getPoliciesByUserId(10L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(policy.getPolicyNumber(), result.get(0).getPolicyNumber());
    }

    
    @Test
    void testUpdatePolicy_NotFound() {
        when(policyRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> policyService.updatePolicy(1L, policyDTO));
        assertEquals("Policy not found with id 1", ex.getMessage());
    }

    @Test
    void testDeletePolicy_Success() {
        when(policyRepository.findById(policy.getId())).thenReturn(Optional.of(policy));

        assertDoesNotThrow(() -> policyService.deletePolicy(policy.getId()));

        verify(policyRepository, times(1)).findById(policy.getId());
        verify(policyRepository, times(1)).delete(policy);
    }

    @Test
    void testDeletePolicy_NotFound() {
        when(policyRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> policyService.deletePolicy(1L));
        assertEquals("Policy not found with id 1", ex.getMessage());
    }

    @Test
    void testGetPolicyByProposalId_Success() {
        when(policyRepository.findByProposalId(proposal.getId())).thenReturn(Optional.of(policy));

        PolicyDTO dto = policyService.getPolicyByProposalId(proposal.getId());

        assertNotNull(dto);
        assertEquals(policy.getPolicyNumber(), dto.getPolicyNumber());
    }

    @Test
    void testGetPolicyByProposalId_NotFound() {
        when(policyRepository.findByProposalId(proposal.getId())).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> policyService.getPolicyByProposalId(proposal.getId()));
        assertEquals("Policy not found for proposalId: " + proposal.getId(), ex.getMessage());
    }
}
