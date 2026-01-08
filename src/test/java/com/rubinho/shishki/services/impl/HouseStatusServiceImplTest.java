package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.dto.HouseStatusDto;
import com.rubinho.shishki.mappers.HouseStatusMapper;
import com.rubinho.shishki.model.HouseStatus;
import com.rubinho.shishki.repository.HouseStatusRepository;
import com.rubinho.shishki.services.HouseStatusService;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HouseStatusServiceImplTest {
    private HouseStatusService houseStatusService;

    @Mock
    private HouseStatusRepository houseStatusRepository;
    @Mock
    private HouseStatusMapper houseStatusMapper;

    @BeforeEach
    void init() {
        houseStatusService = new HouseStatusServiceImpl(houseStatusRepository, houseStatusMapper);
    }

    @Test
    void getAllTest() {
        final HouseStatus entity1 = mock();
        final HouseStatus entity2 = mock();
        final HouseStatusDto dto1 = mock();
        final HouseStatusDto dto2 = mock();

        when(houseStatusRepository.findAll()).thenReturn(List.of(entity1, entity2));
        when(houseStatusMapper.toDto(entity1)).thenReturn(dto1);
        when(houseStatusMapper.toDto(entity2)).thenReturn(dto2);

        assertThat(houseStatusService.getAll())
                .hasSize(2)
                .contains(dto1, dto2);
    }

    @Test
    void getTest() {
        final HouseStatus entity = mock();
        final HouseStatusDto dto = mock();

        when(houseStatusRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(houseStatusMapper.toDto(entity)).thenReturn(dto);

        Optional<HouseStatusDto> actual = houseStatusService.get(1L);

        assertTrue(actual.isPresent());
        assertEquals(dto, actual.get());
    }
}
