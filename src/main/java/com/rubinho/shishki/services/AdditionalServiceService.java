package com.rubinho.shishki.services;

import com.rubinho.shishki.dto.AdditionalServiceDto;

import java.util.List;
import java.util.Optional;

public interface AdditionalServiceService {
    List<AdditionalServiceDto> getAll();

    Optional<AdditionalServiceDto> get(Long id);

    AdditionalServiceDto save(AdditionalServiceDto additionalServiceDto);

    Optional<AdditionalServiceDto> edit(Long id, AdditionalServiceDto additionalServiceDto);

    void delete(Long id);
}
