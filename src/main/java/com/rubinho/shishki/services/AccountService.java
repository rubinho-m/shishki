package com.rubinho.shishki.services;

import com.rubinho.shishki.dto.AccountDto;
import com.rubinho.shishki.dto.RegisterDto;
import com.rubinho.shishki.dto.RegisteredUserDto;
import com.rubinho.shishki.model.Account;

import java.util.Optional;

public interface AccountService {
    RegisteredUserDto register(RegisterDto registerDto, boolean isBootstrap);

    Optional<RegisteredUserDto> authorize(AccountDto accountDto);

    Optional<Account> getAccountByToken(String token);

    Optional<Account> getAccountById(Long id);
}
