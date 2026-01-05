package com.rubinho.shishki.mappers;

import com.rubinho.shishki.dto.BookingRequestDto;
import com.rubinho.shishki.dto.BookingResponseDto;
import com.rubinho.shishki.exceptions.AccountNotFoundException;
import com.rubinho.shishki.exceptions.HouseNotFoundException;
import com.rubinho.shishki.model.Booking;

public interface BookingMapper {
    Booking toEntity(BookingRequestDto bookingRequestDto) throws AccountNotFoundException, HouseNotFoundException;

    BookingResponseDto toDto(Booking booking);
}
