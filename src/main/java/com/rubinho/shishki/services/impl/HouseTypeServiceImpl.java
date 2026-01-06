package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.dto.HouseTypeDto;
import com.rubinho.shishki.mappers.HouseTypeMapper;
import com.rubinho.shishki.repository.HouseTypeRepository;
import com.rubinho.shishki.services.HouseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<HouseTypeDto> get(Long id) {
        return houseTypeRepository.findById(id).map(houseTypeMapper::toDto);
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
    public Optional<HouseTypeDto> edit(Long id, HouseTypeDto houseDto) {
        if (!houseTypeRepository.existsById(id)) {
            return Optional.empty();
        }
        houseDto.setId(id);
        return Optional.of(save(houseDto));
    }

    @Override
    public void delete(Long id) {
        houseTypeRepository.findById(id).ifPresent(houseTypeRepository::delete);
    }
}