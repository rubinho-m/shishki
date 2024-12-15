package com.rubinho.shishki.rest.impl;

import com.rubinho.shishki.dto.AccountDto;
import com.rubinho.shishki.dto.RegisteredUserDto;
import com.rubinho.shishki.rest.AuthApi;
import com.rubinho.shishki.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthApiImpl implements AuthApi {
    private final AccountService accountService;

    @Autowired
    public AuthApiImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public ResponseEntity<RegisteredUserDto> register(AccountDto accountDto) {
        return ResponseEntity.ok(accountService.register(accountDto));
    }

    @Override
    public ResponseEntity<RegisteredUserDto> authorize(AccountDto accountDto) {
        return ResponseEntity.ok(accountService.authorize(accountDto));
    }

    @Override
    public ResponseEntity<Void> requestOwnerRole(String token) {
        accountService.requestOwnerRights(token);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
