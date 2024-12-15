package com.rubinho.shishki.services;

import com.rubinho.shishki.dto.GuestDto;
import com.rubinho.shishki.model.Account;

import java.util.List;
import java.util.Set;

public interface GuestService {
    List<GuestDto> getAll();

    Set<GuestDto> getGuestsByAccount(Account account);

    GuestDto get(Long id);

    GuestDto save(GuestDto guestDto);

    GuestDto edit(Long id, GuestDto guestDto);

    void delete(Long id);
}
