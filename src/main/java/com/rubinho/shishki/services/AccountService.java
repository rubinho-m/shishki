package com.rubinho.shishki.services;

import com.rubinho.shishki.dto.AccountDto;
import com.rubinho.shishki.dto.RegisteredUserDto;
import com.rubinho.shishki.model.Account;

public interface AccountService {
    RegisteredUserDto register(AccountDto accountDto);

    RegisteredUserDto authorize(AccountDto accountDto);

    void requestOwnerRights(String token);

    Account getAccountByToken(String token);

    Account getAccountById(Long id);
}
