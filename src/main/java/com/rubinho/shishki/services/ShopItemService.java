package com.rubinho.shishki.services;

import com.rubinho.shishki.dto.ShopItemDto;

import java.util.List;

public interface ShopItemService {
    List<ShopItemDto> getAll();

    ShopItemDto get(Long id);

    ShopItemDto save(ShopItemDto shopItemDto);

    ShopItemDto edit(Long id, ShopItemDto shopItemDto);

    void delete(Long id);
}
