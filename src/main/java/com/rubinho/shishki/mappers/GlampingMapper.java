package com.rubinho.shishki.mappers;

import com.rubinho.shishki.dto.GlampingDto;
import com.rubinho.shishki.model.Glamping;

public interface GlampingMapper {
    Glamping toEntity(GlampingDto glampingDto);

    GlampingDto toDto(Glamping glamping);
}
