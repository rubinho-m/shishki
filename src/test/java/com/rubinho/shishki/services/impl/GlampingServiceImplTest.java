package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.dto.GlampingRequestDto;
import com.rubinho.shishki.dto.GlampingResponseDto;
import com.rubinho.shishki.exceptions.AccountNotFoundException;
import com.rubinho.shishki.mappers.GlampingMapper;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.model.Glamping;
import com.rubinho.shishki.model.GlampingStatus;
import com.rubinho.shishki.model.Role;
import com.rubinho.shishki.repository.GlampingRepository;
import com.rubinho.shishki.services.GlampingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
public class GlampingServiceImplTest {
    private GlampingService glampingService;

    @Mock
    private GlampingRepository glampingRepository;
    @Mock
    private GlampingMapper glampingMapper;

    @BeforeEach
    void setUp() {
        glampingService = new GlampingServiceImpl(glampingRepository, glampingMapper);
    }

    @Test
    void getAllTest() {
        final Glamping entity1 = mock();
        final Glamping entity2 = mock();
        final GlampingResponseDto dto1 = mock();
        final GlampingResponseDto dto2 = mock();

        when(glampingRepository.findAll()).thenReturn(List.of(entity1, entity2));
        when(glampingMapper.toDto(entity1)).thenReturn(dto1);
        when(glampingMapper.toDto(entity2)).thenReturn(dto2);

        assertThat(glampingService.getAll())
                .hasSize(2)
                .contains(dto1, dto2);
    }

    @Test
    void getAllApprovedTest() {
        final Glamping entity1 = mock();
        final Glamping entity2 = mock();
        final GlampingResponseDto dto1 = mock();
        final GlampingResponseDto dto2 = mock();

        when(glampingRepository.findAllByGlampingStatus(GlampingStatus.APPROVED)).thenReturn(List.of(entity1, entity2));
        when(glampingMapper.toDto(entity1)).thenReturn(dto1);
        when(glampingMapper.toDto(entity2)).thenReturn(dto2);

        assertThat(glampingService.getAllApproved())
                .hasSize(2)
                .contains(dto1, dto2);
    }

    @Test
    void getTest() {
        final Glamping entity = mock();
        final GlampingResponseDto dto = mock();

        when(glampingRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(glampingMapper.toDto(entity)).thenReturn(dto);

        final Optional<GlampingResponseDto> actual = glampingService.get(1L);

        assertTrue(actual.isPresent());
        assertEquals(dto, actual.get());
    }

    @Test
    void saveTest() throws AccountNotFoundException {
        final GlampingRequestDto requestDto = mock();
        final Glamping entity = mock();
        final GlampingResponseDto responseDto = mock();
        final Account account = mock();

        when(glampingMapper.toEntity(requestDto)).thenReturn(entity);
        when(account.getRole()).thenReturn(Role.ADMIN);
        when(glampingRepository.save(entity)).thenReturn(entity);
        when(glampingMapper.toDto(entity)).thenReturn(responseDto);

        assertEquals(responseDto, glampingService.save(requestDto, account));
    }

    @Test
    void editTest() throws AccountNotFoundException {
        final GlampingRequestDto requestDto = mock();
        final Glamping entity = mock();
        final GlampingResponseDto responseDto = mock();
        final Account account = mock();

        when(glampingRepository.existsById(1L)).thenReturn(true);
        when(glampingMapper.toEntity(requestDto)).thenReturn(entity);
        when(account.getRole()).thenReturn(Role.ADMIN);
        when(glampingRepository.save(entity)).thenReturn(entity);
        when(glampingMapper.toDto(entity)).thenReturn(responseDto);

        final Optional<GlampingResponseDto> actual = glampingService.edit(1L, requestDto, account);

        assertTrue(actual.isPresent());
        assertEquals(responseDto, actual.get());
        verify(requestDto).setId(1L);
    }

    @Test
    void deleteTest() {
        final Glamping entity = mock();
        final Account account = mock();

        when(account.getRole()).thenReturn(Role.ADMIN);
        when(glampingRepository.findById(1L)).thenReturn(Optional.of(entity));

        glampingService.delete(1L, account);

        verify(glampingRepository).delete(entity);
    }
}
