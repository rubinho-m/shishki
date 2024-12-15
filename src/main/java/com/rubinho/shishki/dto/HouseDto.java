package com.rubinho.shishki.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class HouseDto {
    private Long id;

    private Long glampingId;

    private String houseType;

    private String houseStatus;

    private Integer cost;

    private String photoName;
}
