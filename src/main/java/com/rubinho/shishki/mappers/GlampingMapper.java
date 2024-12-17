package com.rubinho.shishki.mappers;

import com.rubinho.shishki.dto.GlampingRequestDto;
import com.rubinho.shishki.dto.GlampingResponseDto;
import com.rubinho.shishki.model.Glamping;

public interface GlampingMapper {
    Glamping toEntity(GlampingRequestDto glampingRequestDto);

    GlampingResponseDto toDto(Glamping glamping);
}
