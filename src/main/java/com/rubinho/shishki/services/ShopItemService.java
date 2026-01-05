package com.rubinho.shishki.services;

import com.rubinho.shishki.dto.ShopItemDto;

import java.util.List;
import java.util.Optional;

public interface ShopItemService {
    List<ShopItemDto> getAll();

    Optional<ShopItemDto> get(Long id);

    ShopItemDto save(ShopItemDto shopItemDto);

    Optional<ShopItemDto> edit(Long id, ShopItemDto shopItemDto);

    void delete(Long id);
}
