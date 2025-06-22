package com.hexaware.automobile.services;

import com.hexaware.automobile.dtos.ProposalDTO;
import com.hexaware.automobile.entities.Proposal;
import com.hexaware.automobile.entities.User;
import com.hexaware.automobile.repositories.ProposalRepository;
import com.hexaware.automobile.repositories.UserRepository;
import com.hexaware.automobile.services.impl.ProposalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProposalServiceImplTest {

    @Mock
    private ProposalRepository proposalRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProposalServiceImpl proposalService;

    private User user;
    private Proposal proposal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);

        proposal = new Proposal();
        proposal.setId(100L);
        proposal.setUser(user);
        proposal.setVehicleType("Car");
        proposal.setStatus(Proposal.Status.SUBMITTED);
        proposal.setCreatedAt(LocalDateTime.now());
        proposal.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void testCreateProposal() {
        ProposalDTO dto = new ProposalDTO();
        dto.setId(100L);
        dto.setUserId(1L);
        dto.setVehicleType("Car");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(proposalRepository.save(any(Proposal.class))).thenReturn(proposal);

        Proposal result = proposalService.createProposal(dto);
        assertNotNull(result);
        assertEquals("Car", result.getVehicleType());
    }

    @Test
    void testGetProposalById() {
        when(proposalRepository.findById(100L)).thenReturn(Optional.of(proposal));

        Proposal result = proposalService.getProposalById(100L);
        assertNotNull(result);
        assertEquals(100L, result.getId());
    }

    @Test
    void testUpdateProposal() {
        ProposalDTO dto = new ProposalDTO();
        dto.setId(100L);
        dto.setUserId(1L);
        dto.setVehicleType("Bike");
        dto.setStatus("ACTIVE");

        when(proposalRepository.findById(100L)).thenReturn(Optional.of(proposal));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(proposalRepository.save(any(Proposal.class))).thenReturn(proposal);

        Proposal updated = proposalService.updateProposal(100L, dto);
        assertNotNull(updated);
        assertEquals("Bike", updated.getVehicleType());
        assertEquals(Proposal.Status.ACTIVE, updated.getStatus());
    }

    @Test
    void testDeleteProposal() {
        when(proposalRepository.existsById(100L)).thenReturn(true);
        doNothing().when(proposalRepository).deleteById(100L);

        assertDoesNotThrow(() -> proposalService.deleteProposal(100L));
        verify(proposalRepository, times(1)).deleteById(100L);
    }

    @Test
    void testGetAllProposals() {
        List<Proposal> list = List.of(proposal);
        when(proposalRepository.findAll()).thenReturn(list);

        List<Proposal> result = proposalService.getAllProposals();
        assertEquals(1, result.size());
    }

    @Test
    void testGetProposalsByUserId() {
        List<Proposal> list = List.of(proposal);
        when(proposalRepository.findByUserId(1L)).thenReturn(list);

        List<Proposal> result = proposalService.getProposalsByUserId(1L);
        assertEquals(1, result.size());
    }
}
