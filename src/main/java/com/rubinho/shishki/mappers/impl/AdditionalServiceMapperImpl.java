package com.rubinho.shishki.mappers.impl;

import com.rubinho.shishki.dto.AdditionalServiceDto;
import com.rubinho.shishki.mappers.AdditionalServiceMapper;
import com.rubinho.shishki.model.AdditionalService;
import org.springframework.stereotype.Service;

@Service
public class AdditionalServiceMapperImpl implements AdditionalServiceMapper {
    @Override
    public AdditionalService toEntity(AdditionalServiceDto additionalServiceDto) {
        return AdditionalService.builder()
                .id(additionalServiceDto.getId())
                .name(additionalServiceDto.getName())
                .cost(additionalServiceDto.getCost())
                .description(additionalServiceDto.getDescription())
                .build();
    }

    @Override
    public AdditionalServiceDto toDto(AdditionalService additionalService) {
        return AdditionalServiceDto.builder()
                .id(additionalService.getId())
                .name(additionalService.getName())
                .cost(additionalService.getCost())
                .description(additionalService.getDescription())
                .build();
    }
}
