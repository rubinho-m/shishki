package com.rubinho.shishki.rest.v1.impl;

import com.rubinho.shishki.dto.GuestDto;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.rest.v1.GuestApiV1;
import com.rubinho.shishki.services.AccountService;
import com.rubinho.shishki.services.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@RestController
public class GuestApiV1Impl implements GuestApiV1 {
    private final GuestService guestService;
    private final AccountService accountService;

    @Autowired
    public GuestApiV1Impl(GuestService guestService,
                          AccountService accountService) {
        this.guestService = guestService;
        this.accountService = accountService;
    }

    @Override
    public ResponseEntity<List<GuestDto>> getAll() {
        return ResponseEntity.ok(guestService.getAll());
    }

    @Override
    public ResponseEntity<Set<GuestDto>> getAllByAccount(String token) {
        final Account account = accountService.getAccountByToken(token);
        return ResponseEntity.ok(guestService.getGuestsByAccount(account));
    }

    @Override
    public ResponseEntity<GuestDto> get(Long id) {
        return ResponseEntity.ok(guestService.get(id));
    }

    @Override
    public ResponseEntity<GuestDto> add(GuestDto guestDto) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(guestService.save(guestDto));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone or unique of guest already exists");
        }
    }

    @Override
    public ResponseEntity<GuestDto> edit(Long id, GuestDto newGuestDto) {
        try {
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(guestService.edit(id, newGuestDto));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone or unique of guest already exists");
        }
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        guestService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
