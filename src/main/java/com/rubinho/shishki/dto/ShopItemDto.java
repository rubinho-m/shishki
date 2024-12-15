package com.rubinho.shishki.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ShopItemDto {
    private Long id;

    private String name;

    private String description;

    private Integer price;
}
