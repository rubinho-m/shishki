package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.dto.ShopItemDto;
import com.rubinho.shishki.mappers.ShopItemMapper;
import com.rubinho.shishki.model.ShopItem;
import com.rubinho.shishki.repository.ShopItemRepository;
import com.rubinho.shishki.services.ShopItemService;
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
public class ShopItemServiceImplTest {
    private ShopItemService shopItemService;

    @Mock
    private ShopItemRepository shopItemRepository;

    @Mock
    private ShopItemMapper shopItemMapper;

    @BeforeEach
    void init() {
        shopItemService = new ShopItemServiceImpl(shopItemRepository, shopItemMapper);
    }

    @Test
    void getAllTest() {
        final ShopItem entity1 = mock();
        final ShopItem entity2 = mock();
        final List<ShopItem> shopItems = List.of(entity1, entity2);
        final ShopItemDto dto1 = mock();
        final ShopItemDto dto2 = mock();

        when(shopItemRepository.findAll()).thenReturn(shopItems);
        when(shopItemMapper.toDto(entity1)).thenReturn(dto1);
        when(shopItemMapper.toDto(entity2)).thenReturn(dto2);

        assertThat(shopItemService.getAll())
                .hasSize(2)
                .contains(dto1, dto2);
    }

    @Test
    void getTest() {
        final ShopItem entity = mock();
        final ShopItemDto dto = mock();

        when(shopItemRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(shopItemMapper.toDto(entity)).thenReturn(dto);

        final Optional<ShopItemDto> actual = shopItemService.get(1L);

        assertTrue(actual.isPresent());
        assertEquals(dto, actual.get());
    }

    @Test
    void saveTest() {
        final ShopItemDto dto = mock();
        final ShopItem entity = mock();
        final ShopItemDto saved = mock();

        when(shopItemMapper.toEntity(dto)).thenReturn(entity);
        when(shopItemRepository.save(entity)).thenReturn(entity);
        when(shopItemMapper.toDto(entity)).thenReturn(saved);

        assertEquals(saved, shopItemService.save(dto));
    }

    @Test
    void editTest() {
        final ShopItemDto dto = mock();
        final ShopItem entity = mock();
        final ShopItemDto saved = mock();

        when(shopItemRepository.existsById(1L)).thenReturn(true);
        when(shopItemMapper.toEntity(dto)).thenReturn(entity);
        when(shopItemRepository.save(entity)).thenReturn(entity);
        when(shopItemMapper.toDto(entity)).thenReturn(saved);

        Optional<ShopItemDto> actual = shopItemService.edit(1L, dto);

        assertTrue(actual.isPresent());
        assertEquals(saved, actual.get());

        verify(dto).setId(1L);
    }

    @Test
    void editNotExistentItemTest() {
        when(shopItemRepository.existsById(1L)).thenReturn(false);

        assertFalse(shopItemService.edit(1L, mock()).isPresent());
    }

    @Test
    void deleteTest() {
        final ShopItem entity = mock();

        when(shopItemRepository.findById(1L)).thenReturn(Optional.of(entity));

        shopItemService.delete(1L);

        verify(shopItemRepository).delete(entity);
    }
}
