package com.rubinho.shishki.mappers;

import com.rubinho.shishki.dto.BookingRequestDto;
import com.rubinho.shishki.dto.BookingResponseDto;
import com.rubinho.shishki.model.Booking;

public interface BookingMapper {
    Booking toEntity(BookingRequestDto bookingRequestDto);

    BookingResponseDto toDto(Booking booking);
}
