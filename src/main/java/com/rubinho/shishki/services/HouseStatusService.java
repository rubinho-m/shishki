package com.rubinho.shishki.services;

import com.rubinho.shishki.dto.HouseStatusDto;

import java.util.List;
import java.util.Optional;

public interface HouseStatusService {
    List<HouseStatusDto> getAll();

    Optional<HouseStatusDto> get(Long id);
}
