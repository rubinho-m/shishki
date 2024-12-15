package com.rubinho.shishki.mappers;

import com.rubinho.shishki.dto.ShopItemDto;
import com.rubinho.shishki.model.ShopItem;

public interface ShopItemMapper {
    ShopItem toEntity(ShopItemDto shopItemDto);

    ShopItemDto toDto(ShopItem shopItem);
}
