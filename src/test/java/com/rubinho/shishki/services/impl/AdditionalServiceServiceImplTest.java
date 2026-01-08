package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.dto.AdditionalServiceDto;
import com.rubinho.shishki.mappers.AdditionalServiceMapper;
import com.rubinho.shishki.model.AdditionalService;
import com.rubinho.shishki.repository.AdditionalServiceRepository;
import com.rubinho.shishki.services.AdditionalServiceService;
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
public class AdditionalServiceServiceImplTest {
    private AdditionalServiceService additionalServiceService;

    @Mock
    private AdditionalServiceRepository additionalServiceRepository;
    @Mock
    private AdditionalServiceMapper additionalServiceMapper;

    @BeforeEach
    void setUp() {
        additionalServiceService = new AdditionalServiceServiceImpl(additionalServiceRepository, additionalServiceMapper);
    }

    @Test
    void getAllTest() {
        final AdditionalService entity1 = mock();
        final AdditionalService entity2 = mock();
        final AdditionalServiceDto dto1 = mock();
        final AdditionalServiceDto dto2 = mock();

        when(additionalServiceRepository.findAll()).thenReturn(List.of(entity1, entity2));
        when(additionalServiceMapper.toDto(entity1)).thenReturn(dto1);
        when(additionalServiceMapper.toDto(entity2)).thenReturn(dto2);

        assertThat(additionalServiceService.getAll())
                .hasSize(2)
                .contains(dto1, dto2);
    }

    @Test
    void getTest() {
        final AdditionalService entity = mock();
        final AdditionalServiceDto dto = mock();

        when(additionalServiceRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(additionalServiceMapper.toDto(entity)).thenReturn(dto);

        final Optional<AdditionalServiceDto> actual = additionalServiceService.get(1L);

        assertTrue(actual.isPresent());
        assertEquals(dto, actual.get());
    }

    @Test
    void saveTest() {
        final AdditionalService entity = mock();
        final AdditionalServiceDto dto = mock();

        when(additionalServiceMapper.toEntity(dto)).thenReturn(entity);
        when(additionalServiceRepository.save(entity)).thenReturn(entity);
        when(additionalServiceMapper.toDto(entity)).thenReturn(dto);

        assertEquals(dto, additionalServiceService.save(dto));
    }

    @Test
    void editTest() {
        final AdditionalService entity = mock();
        final AdditionalServiceDto dto = mock();

        when(additionalServiceMapper.toEntity(dto)).thenReturn(entity);
        when(additionalServiceRepository.save(entity)).thenReturn(entity);
        when(additionalServiceMapper.toDto(entity)).thenReturn(dto);
        when(additionalServiceRepository.existsById(1L)).thenReturn(true);

        final Optional<AdditionalServiceDto> actual = additionalServiceService.edit(1L, dto);

        assertTrue(actual.isPresent());
        assertEquals(dto, actual.get());
        verify(dto).setId(1L);
    }

    @Test
    void deleteTest() {
        final AdditionalService entity = mock();

        when(additionalServiceRepository.findById(1L)).thenReturn(Optional.of(entity));

        additionalServiceService.delete(1L);

        verify(additionalServiceRepository).delete(entity);
    }
}
