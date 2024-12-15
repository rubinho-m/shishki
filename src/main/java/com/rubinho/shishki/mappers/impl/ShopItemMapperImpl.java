package com.rubinho.shishki.mappers.impl;

import com.rubinho.shishki.dto.ShopItemDto;
import com.rubinho.shishki.mappers.ShopItemMapper;
import com.rubinho.shishki.model.ShopItem;
import org.springframework.stereotype.Service;

@Service
public class ShopItemMapperImpl implements ShopItemMapper {
    @Override
    public ShopItem toEntity(ShopItemDto shopItemDto) {
        return ShopItem.builder()
                .id(shopItemDto.getId())
                .name(shopItemDto.getName())
                .price(shopItemDto.getPrice())
                .description(shopItemDto.getDescription())
                .build();
    }

    @Override
    public ShopItemDto toDto(ShopItem shopItem) {
        return ShopItemDto.builder()
                .id(shopItem.getId())
                .name(shopItem.getName())
                .description(shopItem.getDescription())
                .price(shopItem.getPrice())
                .build();
    }
}
