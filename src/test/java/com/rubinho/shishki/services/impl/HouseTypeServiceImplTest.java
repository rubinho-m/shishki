package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.dto.HouseTypeDto;
import com.rubinho.shishki.mappers.HouseTypeMapper;
import com.rubinho.shishki.model.HouseType;
import com.rubinho.shishki.repository.HouseTypeRepository;
import com.rubinho.shishki.services.HouseTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HouseTypeServiceImplTest {
    private HouseTypeService houseTypeService;

    @Mock
    private HouseTypeRepository houseTypeRepository;
    @Mock
    private HouseTypeMapper houseTypeMapper;

    @BeforeEach
    void init() {
        houseTypeService = new HouseTypeServiceImpl(houseTypeRepository, houseTypeMapper);
    }

    @Test
    void getAllTest() {
        final HouseType entity1 = mock();
        final HouseType entity2 = mock();
        final HouseTypeDto dto1 = mock();
        final HouseTypeDto dto2 = mock();

        when(houseTypeRepository.findAll()).thenReturn(List.of(entity1, entity2));
        when(houseTypeMapper.toDto(entity1)).thenReturn(dto1);
        when(houseTypeMapper.toDto(entity2)).thenReturn(dto2);

        assertThat(houseTypeService.getAll())
                .hasSize(2)
                .contains(dto1, dto2);
    }

    @Test
    void getTest() {
        final HouseType entity = mock();
        final HouseTypeDto dto = mock();

        when(houseTypeRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(houseTypeMapper.toDto(entity)).thenReturn(dto);

        final Optional<HouseTypeDto> actual = houseTypeService.get(1L);
        assertTrue(actual.isPresent());
        assertEquals(dto, actual.get());
    }

    @Test
    void saveTest() {
        final HouseType entity = mock();
        final HouseTypeDto dto = mock();

        when(houseTypeMapper.toEntity(dto)).thenReturn(entity);
        when(houseTypeRepository.save(entity)).thenReturn(entity);
        when(houseTypeMapper.toDto(entity)).thenReturn(dto);

        assertEquals(dto, houseTypeService.save(dto));
    }

    @Test
    void editTest() {
        final HouseType entity = mock();
        final HouseTypeDto dto = mock();

        when(houseTypeRepository.existsById(1L)).thenReturn(true);
        when(houseTypeMapper.toEntity(dto)).thenReturn(entity);
        when(houseTypeRepository.save(entity)).thenReturn(entity);
        when(houseTypeMapper.toDto(entity)).thenReturn(dto);

        final Optional<HouseTypeDto> actual = houseTypeService.edit(1L, dto);

        assertTrue(actual.isPresent());
        assertEquals(dto, actual.get());

        verify(dto).setId(1L);
    }

    @Test
    void editNotExistentHouseTypeTest() {
        when(houseTypeRepository.existsById(1L)).thenReturn(false);
        assertFalse(houseTypeService.edit(1L, mock()).isPresent());
    }

    @Test
    void deleteTest() {
        final HouseType entity = mock();
        when(houseTypeRepository.findById(1L)).thenReturn(Optional.of(entity));

        houseTypeService.delete(1L);

        verify(houseTypeRepository).delete(entity);
    }
}
