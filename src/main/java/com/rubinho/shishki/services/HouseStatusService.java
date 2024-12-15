package com.rubinho.shishki.services;

import com.rubinho.shishki.dto.HouseStatusDto;

import java.util.List;

public interface HouseStatusService {
    List<HouseStatusDto> getAll();

    HouseStatusDto get(Long id);
}
