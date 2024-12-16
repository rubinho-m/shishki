package com.rubinho.shishki.services;

import com.rubinho.shishki.dto.BookingRequestDto;
import com.rubinho.shishki.dto.BookingResponseDto;
import com.rubinho.shishki.model.Account;

import java.util.List;

public interface BookingService {
    List<BookingResponseDto> getAll();

    List<BookingResponseDto> getAllByAccount(Account account);

    List<BookingResponseDto> getAllByGlamping(Long glampingId, Account account);

    BookingResponseDto get(Long id);

    BookingResponseDto save(BookingRequestDto bookingRequestDto, Account account);

    BookingResponseDto edit(Long id, BookingRequestDto bookingRequestDto, Account account);

    void delete(Long id, Account account);
}
