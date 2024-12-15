package com.rubinho.shishki.mappers;

import com.rubinho.shishki.dto.GuestDto;
import com.rubinho.shishki.model.Guest;

public interface GuestMapper {
    Guest toEntity(GuestDto guestDto);

    GuestDto toDto(Guest guest);
}
