package com.rubinho.shishki.rest.impl;

import com.rubinho.shishki.dto.ShopItemDto;
import com.rubinho.shishki.rest.ShopItemApi;
import com.rubinho.shishki.services.ShopItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ShopItemApiImpl implements ShopItemApi {
    private final ShopItemService shopItemService;

    @Autowired
    public ShopItemApiImpl(ShopItemService shopItemService) {
        this.shopItemService = shopItemService;
    }

    @Override
    public ResponseEntity<List<ShopItemDto>> getAll() {
        return ResponseEntity.ok(shopItemService.getAll());
    }

    @Override
    public ResponseEntity<ShopItemDto> get(Long id) {
        return ResponseEntity.ok(shopItemService.get(id));
    }

    @Override
    public ResponseEntity<ShopItemDto> add(ShopItemDto shopItemDto) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(shopItemService.save(shopItemDto));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Shop item with name: %s already exists".formatted(shopItemDto.getName())
            );
        }
    }

    @Override
    public ResponseEntity<ShopItemDto> edit(Long id, ShopItemDto newShopItemDto) {
        try {
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(shopItemService.edit(id, newShopItemDto));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Shop item with name: %s already exists".formatted(newShopItemDto.getName())
            );
        }
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        shopItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
