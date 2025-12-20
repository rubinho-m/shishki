package com.rubinho.shishki.rest.v1.impl;

import com.rubinho.shishki.dto.AccountDto;
import com.rubinho.shishki.dto.RegisterDto;
import com.rubinho.shishki.dto.RegisteredUserDto;
import com.rubinho.shishki.rest.v1.AuthApiV1;
import com.rubinho.shishki.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthApiV1Impl implements AuthApiV1 {
    private final AccountService accountService;

    @Autowired
    public AuthApiV1Impl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public ResponseEntity<RegisteredUserDto> register(RegisterDto registerDto) {
        return ResponseEntity.ok(accountService.register(registerDto));
    }

    @Override
    public ResponseEntity<RegisteredUserDto> authorize(AccountDto accountDto) {
        return ResponseEntity.ok(accountService.authorize(accountDto));
    }
}
