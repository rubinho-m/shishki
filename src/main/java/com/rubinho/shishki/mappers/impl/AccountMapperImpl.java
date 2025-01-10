package com.rubinho.shishki.mappers.impl;

import com.rubinho.shishki.dto.AccountDto;
import com.rubinho.shishki.dto.PotentialOwnerDto;
import com.rubinho.shishki.dto.RegisterDto;
import com.rubinho.shishki.dto.RegisteredUserDto;
import com.rubinho.shishki.dto.SecuredAccountDto;
import com.rubinho.shishki.mappers.AccountMapper;
import com.rubinho.shishki.mappers.GuestMapper;
import com.rubinho.shishki.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountMapperImpl implements AccountMapper {
    private final GuestMapper guestMapper;

    @Autowired
    public AccountMapperImpl(GuestMapper guestMapper) {
        this.guestMapper = guestMapper;
    }

    @Override
    public Account toEntity(RegisterDto registerDto) {
        return Account.builder()
                .id(registerDto.getId())
                .login(registerDto.getLogin())
                .password(registerDto.getPassword())
                .guest(guestMapper.toEntity(registerDto.getGuest()))
                .role(registerDto.getRole())
                .build();
    }

    @Override
    public AccountDto toDto(Account account) {
        return AccountDto.builder()
                .id(account.getId())
                .login(account.getLogin())
                .password(account.getPassword())
                .guest(guestMapper.toDto(account.getGuest()))
                .build();
    }

    @Override
    public RegisteredUserDto toRegisteredUserDto(Account account) {
        return RegisteredUserDto.builder()
                .id(account.getId())
                .login(account.getLogin())
                .role(account.getRole())
                .guest(guestMapper.toDto(account.getGuest()))
                .build();
    }

    @Override
    public PotentialOwnerDto toPotentialOwnerDto(Account account) {
        return PotentialOwnerDto.builder()
                .id(account.getId())
                .login(account.getLogin())
                .build();
    }

    @Override
    public SecuredAccountDto toSecuredAccountDto(Account account) {
        return SecuredAccountDto.builder()
                .id(account.getId())
                .login(account.getLogin())
                .role(account.getRole())
                .build();
    }
}
