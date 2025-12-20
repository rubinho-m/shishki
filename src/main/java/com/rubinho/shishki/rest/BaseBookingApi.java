package com.rubinho.shishki.rest;

import com.rubinho.shishki.dto.BookingRequestDto;
import com.rubinho.shishki.dto.BookingResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public interface BaseBookingApi {
    @GetMapping("/bookings")
    ResponseEntity<List<BookingResponseDto>> getAll();

    @GetMapping("/bookings/my")
    ResponseEntity<List<BookingResponseDto>> getAllByToken(@RequestHeader("Authorization") String token);

    @GetMapping("/bookings/account/{accountId}")
    ResponseEntity<List<BookingResponseDto>> getAllByAccount(@PathVariable Long accountId);

    @GetMapping("/bookings/glamping/{glampingId}")
    ResponseEntity<List<BookingResponseDto>> getAllByGlamping(@PathVariable Long glampingId,
                                                              @RequestHeader("Authorization") String token);

    @GetMapping("/bookings/{id}")
    ResponseEntity<BookingResponseDto> get(@PathVariable Long id);

    @PostMapping("/bookings")
    ResponseEntity<BookingResponseDto> add(@RequestBody BookingRequestDto bookingRequestDto,
                                           @RequestHeader("Authorization") String token);

    @PutMapping("/bookings/{id}")
    ResponseEntity<BookingResponseDto> edit(@PathVariable Long id,
                                            @RequestBody BookingRequestDto newBookingRequestDto,
                                            @RequestHeader("Authorization") String token);

    @DeleteMapping("/bookings/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id, @RequestHeader("Authorization") String token);
}
