package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.dto.AccountDto;
import com.rubinho.shishki.dto.RegisteredUserDto;
import com.rubinho.shishki.jwt.UserAuthProvider;
import com.rubinho.shishki.mappers.AccountMapper;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.model.Role;
import com.rubinho.shishki.repository.AccountRepository;
import com.rubinho.shishki.repository.GuestRepository;
import com.rubinho.shishki.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.CharBuffer;

@Service
public class AccountServiceImpl implements AccountService {
    private final UserAuthProvider userAuthProvider;
    private final GuestRepository guestRepository;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountServiceImpl(UserAuthProvider userAuthProvider,
                              AccountRepository accountRepository,
                              GuestRepository guestRepository,
                              AccountMapper accountMapper,
                              PasswordEncoder passwordEncoder) {
        this.userAuthProvider = userAuthProvider;
        this.accountRepository = accountRepository;
        this.guestRepository = guestRepository;
        this.accountMapper = accountMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegisteredUserDto register(AccountDto accountDto) {
        final Account account = accountMapper.toEntity(accountDto);
        account.setPassword(passwordEncoder.encode(CharBuffer.wrap(accountDto.getPassword())));
        account.setRole(Role.USER);
        guestRepository.save(account.getGuest());
        accountRepository.save(account);
        final RegisteredUserDto registeredUserDto = accountMapper.toRegisteredUserDto(account);
        registeredUserDto.setToken(userAuthProvider.createToken(account.getLogin(), account.getRole()));
        return registeredUserDto;
    }

    @Override
    public RegisteredUserDto authorize(AccountDto accountDto) {
        final Account account = accountRepository.findByLogin(accountDto.getLogin())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No such account"));

        if (passwordEncoder.matches(CharBuffer.wrap(accountDto.getPassword()), account.getPassword())) {
            final RegisteredUserDto registeredUserDto = accountMapper.toRegisteredUserDto(account);
            registeredUserDto.setToken(userAuthProvider.createToken(account.getLogin(), account.getRole()));
            return registeredUserDto;
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad credentials");
    }

    @Override
    public void requestOwnerRights(String token) {
        final Account account = getAccountByToken(token);
        account.setRole(Role.POTENTIAL_OWNER);
        accountRepository.save(account);
    }

    @Override
    public Account getAccountByToken(String token) {
        final String login = userAuthProvider.getLoginFromJwt(token.split(" ")[1]);
        return accountRepository.findByLogin(login)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No such user"));
    }
}
