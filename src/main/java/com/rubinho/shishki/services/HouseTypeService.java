package com.rubinho.shishki.services;

import com.rubinho.shishki.dto.HouseTypeDto;

import java.util.List;
import java.util.Optional;

public interface HouseTypeService {
    List<HouseTypeDto> getAll();

    Optional<HouseTypeDto> get(Long id);

    HouseTypeDto save(HouseTypeDto houseDto);

    Optional<HouseTypeDto> edit(Long id, HouseTypeDto houseDto);

    void delete(Long id);
}
