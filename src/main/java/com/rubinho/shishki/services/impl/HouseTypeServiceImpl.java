package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.dto.HouseTypeDto;
import com.rubinho.shishki.mappers.HouseTypeMapper;
import com.rubinho.shishki.model.HouseType;
import com.rubinho.shishki.repository.HouseTypeRepository;
import com.rubinho.shishki.services.HouseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class HouseTypeServiceImpl implements HouseTypeService {
    private final HouseTypeRepository houseTypeRepository;
    private final HouseTypeMapper houseTypeMapper;

    @Autowired
    public HouseTypeServiceImpl(HouseTypeRepository houseTypeRepository, HouseTypeMapper houseTypeMapper) {
        this.houseTypeRepository = houseTypeRepository;
        this.houseTypeMapper = houseTypeMapper;
    }

    @Override
    public List<HouseTypeDto> getAll() {
        return houseTypeRepository.findAll()
                .stream()
                .map(houseTypeMapper::toDto)
                .toList();
    }

    @Override
    public HouseTypeDto get(Long id) {
        return houseTypeMapper.toDto(
                houseTypeRepository.findById(id)
                        .orElseThrow(
                                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No type by this id")
                        )
        );
    }

    @Override
    public HouseTypeDto save(HouseTypeDto houseDto) {
        return houseTypeMapper.toDto(
                houseTypeRepository.save(
                        houseTypeMapper.toEntity(houseDto)
                )
        );
    }

    @Override
    public HouseTypeDto edit(Long id, HouseTypeDto houseDto) {
        houseDto.setId(id);
        return save(houseDto);
    }

    @Override
    public void delete(Long id) {
        final HouseType houseType = houseTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No type by this id"));
        houseTypeRepository.delete(houseType);
    }
}
