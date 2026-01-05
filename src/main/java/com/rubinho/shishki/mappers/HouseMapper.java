package com.rubinho.shishki.mappers;

import com.rubinho.shishki.dto.HouseDto;
import com.rubinho.shishki.exceptions.GlampingNotFoundException;
import com.rubinho.shishki.exceptions.HouseStatusNotFoundException;
import com.rubinho.shishki.exceptions.HouseTypeNotFoundException;
import com.rubinho.shishki.model.House;

public interface HouseMapper {
    House toEntity(HouseDto houseDto) throws GlampingNotFoundException, HouseTypeNotFoundException, HouseStatusNotFoundException;

    HouseDto toDto(House house);
}
