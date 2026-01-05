package com.rubinho.shishki.rest;

import com.rubinho.shishki.config.AdminAuthorization;
import com.rubinho.shishki.config.NoAuth;
import com.rubinho.shishki.dto.ShopItemDto;
import com.rubinho.shishki.rest.versions.ApiVersioned;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@ApiVersioned(path = {"/api/v1", "/api/v2"})
public interface ShopItemApi {
    @GetMapping("/shop")
    @NoAuth
    ResponseEntity<List<ShopItemDto>> getAll();

    @GetMapping("/shop/{id}")
    @NoAuth
    ResponseEntity<ShopItemDto> get(@PathVariable Long id);

    @PostMapping("/shop")
    @AdminAuthorization
    ResponseEntity<ShopItemDto> add(@RequestBody ShopItemDto shopItemDto);

    @PutMapping("/shop/{id}")
    @AdminAuthorization
    ResponseEntity<ShopItemDto> edit(@PathVariable Long id,
                                     @RequestBody ShopItemDto newShopItemDto);

    @DeleteMapping("/shop/{id}")
    @AdminAuthorization
    ResponseEntity<Void> delete(@PathVariable Long id);
}
