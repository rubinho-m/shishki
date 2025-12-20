package com.rubinho.shishki.rest;

import com.rubinho.shishki.dto.ShopItemDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface BaseShopItemApi {
    @GetMapping("/shop")
    ResponseEntity<List<ShopItemDto>> getAll();

    @GetMapping("/shop/{id}")
    ResponseEntity<ShopItemDto> get(@PathVariable Long id);

    @PostMapping("/shop")
    ResponseEntity<ShopItemDto> add(@RequestBody ShopItemDto shopItemDto);

    @PutMapping("/shop/{id}")
    ResponseEntity<ShopItemDto> edit(@PathVariable Long id,
                                     @RequestBody ShopItemDto newShopItemDto);

    @DeleteMapping("/shop/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}
