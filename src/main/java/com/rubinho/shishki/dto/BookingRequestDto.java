package com.rubinho.shishki.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class BookingRequestDto {
    private Long id;

    private String login;

    private Long houseId;

    private List<Long> guests;

    private LocalDate dateStart;

    private LocalDate dateEnd;

    private List<Long> shopItems;

    private List<Long> services;
}
