package com.rubinho.shishki.rest;

import com.rubinho.shishki.dto.GlampingRequestDto;
import com.rubinho.shishki.dto.GlampingResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public interface GlampingApi {
    @GetMapping("/glampings")
    ResponseEntity<List<GlampingResponseDto>> getAll();

    @GetMapping("/glampings/{id}")
    ResponseEntity<GlampingResponseDto> get(@PathVariable("id") Long id);

    @PostMapping("/glampings")
    ResponseEntity<GlampingResponseDto> add(@RequestBody GlampingRequestDto glampingRequestDto,
                                           @RequestHeader("Authorization") String token);

    @PutMapping("/glampings/{id}")
    ResponseEntity<GlampingResponseDto> edit(@PathVariable("id") Long id,
                                            @RequestBody GlampingRequestDto newGlampingRequestDto,
                                            @RequestHeader("Authorization") String token);

    @DeleteMapping("/glampings/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") Long id, @RequestHeader("Authorization") String token);
}
