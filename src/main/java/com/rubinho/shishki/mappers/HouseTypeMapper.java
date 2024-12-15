package com.rubinho.shishki.mappers;

import com.rubinho.shishki.dto.HouseTypeDto;
import com.rubinho.shishki.model.HouseType;

public interface HouseTypeMapper {
    HouseType toEntity(HouseTypeDto houseTypeDto);

    HouseTypeDto toDto(HouseType houseType);
}
