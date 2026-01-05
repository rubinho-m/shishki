package com.rubinho.shishki.services;

import com.rubinho.shishki.dto.BookingRequestDto;
import com.rubinho.shishki.dto.BookingResponseDto;
import com.rubinho.shishki.exceptions.AccountNotFoundException;
import com.rubinho.shishki.exceptions.HouseNotFoundException;
import com.rubinho.shishki.exceptions.rest.BookingValidationException;
import com.rubinho.shishki.model.Account;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    List<BookingResponseDto> getAll();

    List<BookingResponseDto> getAllByAccount(Account account);

    List<BookingResponseDto> getAllByGlamping(Long glampingId, Account account);

    Optional<BookingResponseDto> get(Long id);

    BookingResponseDto save(BookingRequestDto bookingRequestDto, Account account) throws BookingValidationException, HouseNotFoundException, AccountNotFoundException;

    Optional<BookingResponseDto> edit(Long id, BookingRequestDto bookingRequestDto, Account account) throws BookingValidationException, HouseNotFoundException, AccountNotFoundException;

    void delete(Long id, Account account);
}
