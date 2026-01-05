package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.dto.ShopItemDto;
import com.rubinho.shishki.mappers.ShopItemMapper;
import com.rubinho.shishki.repository.ShopItemRepository;
import com.rubinho.shishki.services.ShopItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<ShopItemDto> get(Long id) {
        return shopItemRepository.findById(id).map(shopItemMapper::toDto);
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
    public Optional<ShopItemDto> edit(Long id, ShopItemDto shopItemDto) {
        if (!shopItemRepository.existsById(id)) {
            return Optional.empty();
        }
        shopItemDto.setId(id);
        return Optional.of(save(shopItemDto));
    }

    @Override
    public void delete(Long id) {
        shopItemRepository.findById(id).ifPresent(shopItemRepository::delete);
    }
}
