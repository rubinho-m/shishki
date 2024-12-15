package com.rubinho.shishki.services;

import com.rubinho.shishki.dto.AdditionalServiceDto;

import java.util.List;

public interface AdditionalServiceService {
    List<AdditionalServiceDto> getAll();

    AdditionalServiceDto get(Long id);

    AdditionalServiceDto save(AdditionalServiceDto additionalServiceDto);

    AdditionalServiceDto edit(Long id, AdditionalServiceDto additionalServiceDto);

    void delete(Long id);
}
