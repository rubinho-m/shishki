package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.dto.AccountDto;
import com.rubinho.shishki.dto.RegisterDto;
import com.rubinho.shishki.dto.RegisteredUserDto;
import com.rubinho.shishki.exceptions.ForbiddenException;
import com.rubinho.shishki.exceptions.UnauthorizedException;
import com.rubinho.shishki.jwt.UserAuthProvider;
import com.rubinho.shishki.mappers.AccountMapper;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.model.Role;
import com.rubinho.shishki.repository.AccountRepository;
import com.rubinho.shishki.repository.GuestRepository;
import com.rubinho.shishki.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;
import java.util.Set;

@Service
public class AccountServiceImpl implements AccountService {
    private final UserAuthProvider userAuthProvider;
    private final GuestRepository guestRepository;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;

    private final Set<Role> ALLOWED_REGISTERING_ROLES = Set.of(Role.USER, Role.OWNER);

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
    public RegisteredUserDto register(RegisterDto registerDto) {
        if (!ALLOWED_REGISTERING_ROLES.contains(registerDto.getRole())) {
            throw new ForbiddenException();
        }
        final Account account = accountMapper.toEntity(registerDto);
        account.setPassword(passwordEncoder.encode(CharBuffer.wrap(registerDto.getPassword())));
        guestRepository.save(account.getGuest());
        accountRepository.save(account);
        final RegisteredUserDto registeredUserDto = accountMapper.toRegisteredUserDto(account);
        registeredUserDto.setToken(userAuthProvider.createToken(account.getLogin(), account.getRole()));
        return registeredUserDto;
    }

    @Override
    public Optional<RegisteredUserDto> authorize(AccountDto accountDto) {
        return accountRepository.findByLogin(accountDto.getLogin())
                .map(account -> {
                    if (passwordEncoder.matches(CharBuffer.wrap(accountDto.getPassword()), account.getPassword())) {
                        final RegisteredUserDto registeredUserDto = accountMapper.toRegisteredUserDto(account);
                        registeredUserDto.setToken(userAuthProvider.createToken(account.getLogin(), account.getRole()));
                        return registeredUserDto;
                    }
                    throw new UnauthorizedException();
                });
    }

    @Override
    public Optional<Account> getAccountByToken(String token) {
        final String login = userAuthProvider.getLoginFromJwt(token.split(" ")[1]);
        return accountRepository.findByLogin(login);
    }

    @Override
    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }
}
