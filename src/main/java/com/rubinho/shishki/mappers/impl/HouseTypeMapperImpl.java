package com.rubinho.shishki.mappers.impl;

import com.rubinho.shishki.dto.HouseTypeDto;
import com.rubinho.shishki.mappers.HouseTypeMapper;
import com.rubinho.shishki.model.HouseType;
import org.springframework.stereotype.Service;

@Service
public class HouseTypeMapperImpl implements HouseTypeMapper {
    @Override
    public HouseType toEntity(HouseTypeDto houseTypeDto) {
        return HouseType.builder()
                .id(houseTypeDto.getId())
                .type(houseTypeDto.getType())
                .numberOfPersons(houseTypeDto.getNumberOfPersons())
                .build();
    }

    @Override
    public HouseTypeDto toDto(HouseType houseType) {
        return HouseTypeDto.builder()
                .id(houseType.getId())
                .type(houseType.getType())
                .numberOfPersons(houseType.getNumberOfPersons())
                .build();
    }
}
