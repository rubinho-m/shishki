package com.rubinho.shishki.services;

import com.rubinho.shishki.dto.GlampingRequestDto;
import com.rubinho.shishki.dto.GlampingResponseDto;
import com.rubinho.shishki.model.Account;

import java.util.List;

public interface GlampingService {
    List<GlampingResponseDto> getAll();

    List<GlampingResponseDto> getAllApproved();

    GlampingResponseDto get(Long id);

    GlampingResponseDto save(GlampingRequestDto glampingRequestDto, Account account);

    GlampingResponseDto edit(Long id, GlampingRequestDto glampingRequestDto, Account account);

    void delete(Long id, Account account);
}
