package com.rubinho.shishki.mappers.impl;

import com.rubinho.shishki.dto.HouseDto;
import com.rubinho.shishki.exceptions.rest.NotFoundException;
import com.rubinho.shishki.mappers.HouseMapper;
import com.rubinho.shishki.model.Glamping;
import com.rubinho.shishki.model.House;
import com.rubinho.shishki.model.HouseStatus;
import com.rubinho.shishki.model.HouseType;
import com.rubinho.shishki.repository.GlampingRepository;
import com.rubinho.shishki.repository.HouseStatusRepository;
import com.rubinho.shishki.repository.HouseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseMapperImpl implements HouseMapper {
    private final GlampingRepository glampingRepository;
    private final HouseTypeRepository houseTypeRepository;
    private final HouseStatusRepository houseStatusRepository;

    @Autowired
    public HouseMapperImpl(GlampingRepository glampingRepository,
                           HouseTypeRepository houseTypeRepository,
                           HouseStatusRepository houseStatusRepository) {
        this.glampingRepository = glampingRepository;
        this.houseTypeRepository = houseTypeRepository;
        this.houseStatusRepository = houseStatusRepository;
    }

    @Override
    public House toEntity(HouseDto houseDto) {
        final HouseType houseType = houseTypeRepository.findByType(houseDto.getHouseType())
                .orElseThrow(
                        () -> new NotFoundException("Not found house type: %s".formatted(houseDto.getHouseType()))
                );
        final HouseStatus houseStatus = houseStatusRepository.findByStatus(houseDto.getHouseStatus())
                .orElseThrow(
                        () -> new NotFoundException("Not found house status: %s".formatted(houseDto.getHouseStatus()))
                );
        final Glamping glamping = glampingRepository.findById(houseDto.getGlampingId())
                .orElseThrow(
                        () -> new NotFoundException("Not found glamping by id: %d".formatted(houseDto.getGlampingId()))
                );
        return House.builder()
                .id(houseDto.getId())
                .houseType(houseType)
                .status(houseStatus)
                .cost(houseDto.getCost())
                .glamping(glamping)
                .photoName(houseDto.getPhotoName())
                .build();
    }

    @Override
    public HouseDto toDto(House house) {
        return HouseDto.builder()
                .id(house.getId())
                .houseType(house.getHouseType().getType())
                .houseStatus(house.getStatus().getStatus())
                .glampingId(house.getGlamping().getId())
                .cost(house.getCost())
                .photoName(house.getPhotoName())
                .build();
    }
}
