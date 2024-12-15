package com.rubinho.shishki.rest;

import com.rubinho.shishki.dto.GuestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Set;

public interface GuestApi {
    @GetMapping("/guests")
    ResponseEntity<List<GuestDto>> getAll();

    @GetMapping("/guests/account")
    ResponseEntity<Set<GuestDto>> getAllByAccount(@RequestHeader("Authorization") String token);

    @GetMapping("/guests/{id}")
    ResponseEntity<GuestDto> get(@PathVariable("id") Long id);

    @PostMapping("/guests")
    ResponseEntity<GuestDto> add(@RequestBody GuestDto guestDto);

    @PutMapping("/guests/{id}")
    ResponseEntity<GuestDto> edit(@PathVariable("id") Long id,
                                  @RequestBody GuestDto newGuestDto);

    @DeleteMapping("/guests/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") Long id);
}
