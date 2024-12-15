package com.rubinho.shishki.mappers;

import com.rubinho.shishki.dto.HouseStatusDto;
import com.rubinho.shishki.model.HouseStatus;

public interface HouseStatusMapper {
    HouseStatus toEntity(HouseStatusDto houseStatusDto);

    HouseStatusDto toDto(HouseStatus houseStatus);
}
