package com.rubinho.shishki.mappers.impl;

import com.rubinho.shishki.dto.GuestDto;
import com.rubinho.shishki.mappers.GuestMapper;
import com.rubinho.shishki.model.Guest;
import org.springframework.stereotype.Service;

@Service
public class GuestMapperImpl implements GuestMapper {
    @Override
    public Guest toEntity(GuestDto guestDto) {
        return Guest.builder()
                .id(guestDto.getId())
                .name(guestDto.getName())
                .surname(guestDto.getSurname())
                .phone(guestDto.getPhone())
                .email(guestDto.getEmail())
                .build();
    }

    @Override
    public GuestDto toDto(Guest guest) {
        return GuestDto.builder()
                .id(guest.getId())
                .name(guest.getName())
                .surname(guest.getSurname())
                .phone(guest.getPhone())
                .email(guest.getEmail())
                .build();
    }
}
