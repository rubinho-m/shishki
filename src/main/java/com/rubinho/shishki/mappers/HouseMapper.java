package com.rubinho.shishki.mappers;

import com.rubinho.shishki.dto.HouseDto;
import com.rubinho.shishki.model.House;

public interface HouseMapper {
    House toEntity(HouseDto houseDto);

    HouseDto toDto(House house);
}
