package com.rubinho.shishki.services;

import com.rubinho.shishki.dto.GlampingDto;
import com.rubinho.shishki.model.Account;

import java.util.List;

public interface GlampingService {
    List<GlampingDto> getAll();

    GlampingDto get(Long id);

    GlampingDto save(GlampingDto glampingDto, Account account);

    GlampingDto edit(Long id, GlampingDto glampingDto, Account account);

    void delete(Long id, Account account);
}
