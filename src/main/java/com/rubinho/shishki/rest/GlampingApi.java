package com.rubinho.shishki.rest;

import com.rubinho.shishki.config.AdminAuthorization;
import com.rubinho.shishki.config.NoAuth;
import com.rubinho.shishki.config.OwnerAuthorization;
import com.rubinho.shishki.dto.GlampingRequestDto;
import com.rubinho.shishki.dto.GlampingResponseDto;
import com.rubinho.shishki.rest.versions.ApiVersioned;
import jakarta.servlet.http.HttpServletRequest;
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
public interface GlampingApi {
    @GetMapping("/glampings")
    @NoAuth
    ResponseEntity<List<GlampingResponseDto>> getAll();

    @GetMapping("/glampings/approved")
    @NoAuth
    ResponseEntity<List<GlampingResponseDto>> getAllApproved();

    @GetMapping("/glampings/{id}")
    @NoAuth
    ResponseEntity<GlampingResponseDto> get(@PathVariable Long id);

    @PostMapping("/glampings")
    @NoAuth
    ResponseEntity<GlampingResponseDto> add(HttpServletRequest httpServletRequest,
                                            @RequestBody GlampingRequestDto glampingRequestDto,
                                            @RequestHeader("Authorization") String token);

    @PutMapping("/glampings/{id}")
    @OwnerAuthorization
    ResponseEntity<GlampingResponseDto> edit(HttpServletRequest httpServletRequest,
                                             @PathVariable Long id,
                                             @RequestBody GlampingRequestDto newGlampingRequestDto,
                                             @RequestHeader("Authorization") String token);

    @DeleteMapping("/glampings/{id}")
    @OwnerAuthorization
    ResponseEntity<Void> delete(@PathVariable Long id, @RequestHeader("Authorization") String token);
}
