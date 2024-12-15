package com.rubinho.shishki.mappers;

import com.rubinho.shishki.dto.AccountDto;
import com.rubinho.shishki.dto.PotentialOwnerDto;
import com.rubinho.shishki.dto.RegisteredUserDto;
import com.rubinho.shishki.model.Account;

public interface AccountMapper {
    Account toEntity(AccountDto accountDto);

    AccountDto toDto(Account account);

    RegisteredUserDto toRegisteredUserDto(Account account);

    PotentialOwnerDto toPotentialOwnerDto(Account account);
}
