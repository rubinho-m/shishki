package com.rubinho.shishki.rest.impl;

import com.rubinho.shishki.dto.BookingRequestDto;
import com.rubinho.shishki.dto.BookingResponseDto;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.model.Glamping;
import com.rubinho.shishki.rest.BookingApi;
import com.rubinho.shishki.services.AccountService;
import com.rubinho.shishki.services.BookingService;
import com.rubinho.shishki.services.GlampingService;
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
        final Account account = accountService.getAccountByToken(token);
        return ResponseEntity.ok(bookingService.getAllByAccountId(account.getId()));
    }

    @Override
    public ResponseEntity<List<BookingResponseDto>> getAllByAccount(Long accountId) {
        return ResponseEntity.ok(bookingService.getAllByAccountId(accountId));
    }

    @Override
    public ResponseEntity<List<BookingResponseDto>> getAllByGlamping(Long glampingId, String token) {
        final Account account = accountService.getAccountByToken(token);
        return ResponseEntity.ok(bookingService.getAllByGlamping(glampingId, account));
    }

    @Override
    public ResponseEntity<BookingResponseDto> get(Long id) {
        return ResponseEntity.ok(bookingService.get(id));
    }

    @Override
    public ResponseEntity<BookingResponseDto> add(BookingRequestDto bookingRequestDto, String token) {
        final Account account = accountService.getAccountByToken(token);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookingService.save(bookingRequestDto, account));
    }

    @Override
    public ResponseEntity<BookingResponseDto> edit(Long id, BookingRequestDto newBookingRequestDto, String token) {
        final Account account = accountService.getAccountByToken(token);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(bookingService.edit(id, newBookingRequestDto, account));
    }

    @Override
    public ResponseEntity<Void> delete(Long id, String token) {
        final Account account = accountService.getAccountByToken(token);
        bookingService.delete(id, account);
        return ResponseEntity.noContent().build();
    }
}
