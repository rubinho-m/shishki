package com.rubinho.shishki.mappers;

import com.rubinho.shishki.dto.AdditionalServiceDto;
import com.rubinho.shishki.model.AdditionalService;

public interface AdditionalServiceMapper {
    AdditionalService toEntity(AdditionalServiceDto additionalServiceDto);

    AdditionalServiceDto toDto(AdditionalService additionalService);
}
