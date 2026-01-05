package com.rubinho.shishki.rest;

import com.rubinho.shishki.config.AdminAuthorization;
import com.rubinho.shishki.config.Authenticated;
import com.rubinho.shishki.dto.BookingRequestDto;
import com.rubinho.shishki.dto.BookingResponseDto;
import com.rubinho.shishki.rest.versions.ApiVersioned;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@ApiVersioned(path = {"/api/v1", "/api/v2"})
public interface BookingApi {
    @GetMapping("/bookings")
    @AdminAuthorization
    ResponseEntity<List<BookingResponseDto>> getAll();

    @GetMapping("/bookings/my")
    @Authenticated
    ResponseEntity<List<BookingResponseDto>> getAllByToken(@RequestHeader("Authorization") String token);

    @GetMapping("/bookings/account/{accountId}")
    @AdminAuthorization
    ResponseEntity<List<BookingResponseDto>> getAllByAccount(@PathVariable Long accountId);

    @GetMapping("/bookings/glamping/{glampingId}")
    @AdminAuthorization
    ResponseEntity<List<BookingResponseDto>> getAllByGlamping(@PathVariable Long glampingId,
                                                              @RequestHeader("Authorization") String token);

    @GetMapping("/bookings/{id}")
    @Authenticated
    ResponseEntity<BookingResponseDto> get(@PathVariable Long id);

    @PostMapping("/bookings")
    @Authenticated
    ResponseEntity<BookingResponseDto> add(@RequestBody BookingRequestDto bookingRequestDto,
                                           @RequestHeader("Authorization") String token);

    @PutMapping("/bookings/{id}")
    @Authenticated
    ResponseEntity<BookingResponseDto> edit(@PathVariable Long id,
                                            @RequestBody BookingRequestDto newBookingRequestDto,
                                            @RequestHeader("Authorization") String token);

    @DeleteMapping("/bookings/{id}")
    @Authenticated
    ResponseEntity<Void> delete(@PathVariable Long id, @RequestHeader("Authorization") String token);
}
