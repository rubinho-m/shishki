package com.rubinho.shishki.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class BookingResponseDto {
    private Long id;

    private String login;

    private Integer uniqueKey;

    private HouseDto house;

    private List<GuestDto> guests;

    private LocalDate dateStart;

    private LocalDate dateEnd;

    private List<ShopItemDto> shopItems;

    private List<AdditionalServiceDto> services;
}
