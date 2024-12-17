package com.rubinho.shishki.services;

import com.rubinho.shishki.dto.AccountDto;
import com.rubinho.shishki.dto.RegisterDto;
import com.rubinho.shishki.dto.RegisteredUserDto;
import com.rubinho.shishki.model.Account;

public interface AccountService {
    RegisteredUserDto register(RegisterDto registerDto);

    RegisteredUserDto authorize(AccountDto accountDto);

    Account getAccountByToken(String token);

    Account getAccountById(Long id);
}
