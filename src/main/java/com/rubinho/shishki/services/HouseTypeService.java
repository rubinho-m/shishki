package com.rubinho.shishki.services;

import com.rubinho.shishki.dto.HouseTypeDto;

import java.util.List;

public interface HouseTypeService {
    List<HouseTypeDto> getAll();

    HouseTypeDto get(Long id);

    HouseTypeDto save(HouseTypeDto houseDto);

    HouseTypeDto edit(Long id, HouseTypeDto houseDto);

    void delete(Long id);
}
