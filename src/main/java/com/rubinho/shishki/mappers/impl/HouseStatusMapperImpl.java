package com.rubinho.shishki.mappers.impl;

import com.rubinho.shishki.dto.HouseStatusDto;
import com.rubinho.shishki.mappers.HouseStatusMapper;
import com.rubinho.shishki.model.HouseStatus;
import org.springframework.stereotype.Service;

@Service
public class HouseStatusMapperImpl implements HouseStatusMapper {
    @Override
    public HouseStatus toEntity(HouseStatusDto houseStatusDto) {
        return HouseStatus.builder()
                .id(houseStatusDto.getId())
                .status(houseStatusDto.getStatus())
                .build();
    }

    @Override
    public HouseStatusDto toDto(HouseStatus houseStatus) {
        return HouseStatusDto.builder()
                .id(houseStatus.getId())
                .status(houseStatus.getStatus())
                .build();
    }
}
