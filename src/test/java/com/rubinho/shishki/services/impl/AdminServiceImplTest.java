package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.dto.GlampingResponseDto;
import com.rubinho.shishki.dto.PotentialOwnerDto;
import com.rubinho.shishki.dto.SecuredAccountDto;
import com.rubinho.shishki.mappers.AccountMapper;
import com.rubinho.shishki.mappers.GlampingMapper;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.model.Glamping;
import com.rubinho.shishki.model.GlampingStatus;
import com.rubinho.shishki.model.Role;
import com.rubinho.shishki.repository.AccountRepository;
import com.rubinho.shishki.repository.GlampingRepository;
import com.rubinho.shishki.services.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminServiceImplTest {
    private AdminService adminService;

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private GlampingRepository glampingRepository;
    @Mock
    private AccountMapper accountMapper;
    @Mock
    private GlampingMapper glampingMapper;

    @BeforeEach
    void setUp() {
        adminService = new AdminServiceImpl(accountRepository, glampingRepository, accountMapper, glampingMapper);
    }

    @Test
    void getAllPotentialOwnersTest() {
        final Account entity1 = mock();
        final Account entity2 = mock();
        final PotentialOwnerDto dto1 = mock();
        final PotentialOwnerDto dto2 = mock();

        when(accountRepository.findAllByRole(Role.POTENTIAL_OWNER)).thenReturn(List.of(entity1, entity2));
        when(accountMapper.toPotentialOwnerDto(entity1)).thenReturn(dto1);
        when(accountMapper.toPotentialOwnerDto(entity2)).thenReturn(dto2);

        assertThat(adminService.getAllPotentialOwners())
                .hasSize(2)
                .contains(dto1, dto2);
    }

    @Test
    void getAllAccounts() {
        final Account entity1 = mock();
        final Account entity2 = mock();
        final SecuredAccountDto dto1 = mock();
        final SecuredAccountDto dto2 = mock();

        when(accountRepository.findAll()).thenReturn(List.of(entity1, entity2));
        when(accountMapper.toSecuredAccountDto(entity1)).thenReturn(dto1);
        when(accountMapper.toSecuredAccountDto(entity2)).thenReturn(dto2);

        assertThat(adminService.getAllAccounts())
                .hasSize(2)
                .contains(dto1, dto2);
    }

    @Test
    void getAllGlampingsForReviewTest() {
        final Glamping entity1 = mock();
        final Glamping entity2 = mock();
        final GlampingResponseDto dto1 = mock();
        final GlampingResponseDto dto2 = mock();

        when(glampingRepository.findAllByGlampingStatus(GlampingStatus.WAITING_FOR_CONFIRMATION))
                .thenReturn(List.of(entity1, entity2));
        when(glampingMapper.toDto(entity1)).thenReturn(dto1);
        when(glampingMapper.toDto(entity2)).thenReturn(dto2);

        assertThat(adminService.getAllGlampingsForReview())
                .hasSize(2)
                .contains(dto1, dto2);
    }

    @EnumSource(GlampingStatus.class)
    @ParameterizedTest
    void setNewGlampingStatusTest(GlampingStatus glampingStatus) {
        final Glamping entity = mock();

        when(glampingRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(glampingRepository.save(entity)).thenReturn(entity);

        final Optional<Glamping> actual = adminService.setNewGlampingStatus(1L, glampingStatus);

        assertTrue(actual.isPresent());
        assertEquals(entity, actual.get());

        verify(entity).setGlampingStatus(glampingStatus);
    }

    @EnumSource(Role.class)
    @ParameterizedTest
    void setNewRoleTest(Role role) {
        final Account entity = mock();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(accountRepository.save(entity)).thenReturn(entity);

        final Optional<Account> actual = adminService.setNewRole(1L, role);

        assertTrue(actual.isPresent());
        assertEquals(entity, actual.get());

        verify(entity).setRole(role);
    }
}
