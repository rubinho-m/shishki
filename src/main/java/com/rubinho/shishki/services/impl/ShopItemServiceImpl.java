package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.dto.ShopItemDto;
import com.rubinho.shishki.mappers.ShopItemMapper;
import com.rubinho.shishki.model.ShopItem;
import com.rubinho.shishki.repository.ShopItemRepository;
import com.rubinho.shishki.services.ShopItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ShopItemServiceImpl implements ShopItemService {
    private final ShopItemRepository shopItemRepository;
    private final ShopItemMapper shopItemMapper;

    @Autowired
    public ShopItemServiceImpl(ShopItemRepository shopItemRepository,
                               ShopItemMapper shopItemMapper) {
        this.shopItemRepository = shopItemRepository;
        this.shopItemMapper = shopItemMapper;
    }

    @Override
    public List<ShopItemDto> getAll() {
        return shopItemRepository.findAll()
                .stream()
                .map(shopItemMapper::toDto)
                .toList();
    }

    @Override
    public ShopItemDto get(Long id) {
        return shopItemMapper.toDto(
                shopItemRepository.findById(id)
                        .orElseThrow(
                                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No item by this id")
                        )
        );
    }

    @Override
    public ShopItemDto save(ShopItemDto shopItemDto) {
        return shopItemMapper.toDto(
                shopItemRepository.save(
                        shopItemMapper.toEntity(shopItemDto)
                )
        );
    }

    @Override
    public ShopItemDto edit(Long id, ShopItemDto shopItemDto) {
        shopItemDto.setId(id);
        return save(shopItemDto);
    }

    @Override
    public void delete(Long id) {
        final ShopItem shopItem = shopItemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No item by this id"));
        shopItemRepository.delete(shopItem);
    }
}
