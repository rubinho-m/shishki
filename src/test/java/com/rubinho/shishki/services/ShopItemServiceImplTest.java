package com.rubinho.shishki.services;

import com.rubinho.shishki.dto.ShopItemDto;
import com.rubinho.shishki.mappers.ShopItemMapper;
import com.rubinho.shishki.model.ShopItem;
import com.rubinho.shishki.repository.ShopItemRepository;
import com.rubinho.shishki.services.impl.ShopItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
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
    void saveTest() {
        final ShopItemDto dto = mock();
        final ShopItem entity = mock();
        final ShopItemDto saved = mock();

        when(shopItemMapper.toEntity(dto)).thenReturn(entity);
        when(shopItemRepository.save(entity)).thenReturn(entity);
        when(shopItemMapper.toDto(entity)).thenReturn(saved);

        assertEquals(saved, shopItemService.save(dto));
    }
}
