package com.rubinho.shishki.rest.impl;

import com.rubinho.shishki.dto.BookingRequestDto;
import com.rubinho.shishki.dto.BookingResponseDto;
import com.rubinho.shishki.exceptions.AccountNotFoundException;
import com.rubinho.shishki.exceptions.HouseNotFoundException;
import com.rubinho.shishki.exceptions.rest.BadRequestException;
import com.rubinho.shishki.exceptions.rest.BookingValidationException;
import com.rubinho.shishki.exceptions.rest.NotFoundException;
import com.rubinho.shishki.exceptions.rest.UnauthorizedException;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.rest.BookingApi;
import com.rubinho.shishki.services.AccountService;
import com.rubinho.shishki.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookingApiImpl implements BookingApi {
    private final BookingService bookingService;
    private final AccountService accountService;

    @Autowired
    public BookingApiImpl(BookingService bookingService,
                          AccountService accountService) {
        this.bookingService = bookingService;
        this.accountService = accountService;
    }

    @Override
    public ResponseEntity<List<BookingResponseDto>> getAll() {
        return ResponseEntity.ok(bookingService.getAll());
    }

    @Override
    public ResponseEntity<List<BookingResponseDto>> getAllByToken(String token) {
        final Account account = getAccount(token);
        return ResponseEntity.ok(bookingService.getAllByAccount(account));
    }

    @Override
    public ResponseEntity<List<BookingResponseDto>> getAllByAccount(Long accountId) {
        final Account account = accountService.getAccountById(accountId)
                .orElseThrow(() -> new NotFoundException("User with id %d not found".formatted(accountId)));
        return ResponseEntity.ok(bookingService.getAllByAccount(account));
    }

    @Override
    public ResponseEntity<List<BookingResponseDto>> getAllByGlamping(Long glampingId, String token) {
        final Account account = getAccount(token);
        return ResponseEntity.ok(bookingService.getAllByGlamping(glampingId, account));
    }

    @Override
    public ResponseEntity<BookingResponseDto> get(Long id) {
        return ResponseEntity.ok(
                bookingService.get(id)
                        .orElseThrow(
                                () -> new NotFoundException("Booking with id %d not found".formatted(id))
                        )
        );
    }

    @Override
    public ResponseEntity<BookingResponseDto> add(BookingRequestDto bookingRequestDto, String token) {
        final Account account = getAccount(token);
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(bookingService.save(bookingRequestDto, account));
        } catch (BookingValidationException e) {
            throw new BadRequestException(e.getMessage());
        } catch (HouseNotFoundException | AccountNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<BookingResponseDto> edit(Long id, BookingRequestDto newBookingRequestDto, String token) {
        final Account account = getAccount(token);
        try {
            final BookingResponseDto booking = bookingService.edit(id, newBookingRequestDto, account)
                    .orElseThrow(
                            () -> new NotFoundException("Booking with id %d not found".formatted(id))
                    );
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(booking);
        } catch (BookingValidationException e) {
            throw new BadRequestException(e.getMessage());
        } catch (HouseNotFoundException | AccountNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Void> delete(Long id, String token) {
        final Account account = getAccount(token);
        bookingService.delete(id, account);
        return ResponseEntity.noContent().build();
    }

    private Account getAccount(String token) {
        return accountService.getAccountByToken(token)
                .orElseThrow(() -> new UnauthorizedException("Not found user by auth token"));
    }
}
