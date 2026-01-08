package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.dto.AccountDto;
import com.rubinho.shishki.dto.RegisterDto;
import com.rubinho.shishki.dto.RegisteredUserDto;
import com.rubinho.shishki.jwt.JwtUtils;
import com.rubinho.shishki.jwt.UserAuthProvider;
import com.rubinho.shishki.mappers.AccountMapper;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.model.Guest;
import com.rubinho.shishki.model.Role;
import com.rubinho.shishki.repository.AccountRepository;
import com.rubinho.shishki.repository.GuestRepository;
import com.rubinho.shishki.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {
    private AccountService accountService;

    @Mock
    private UserAuthProvider userAuthProvider;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private GuestRepository guestRepository;
    @Mock
    private AccountMapper accountMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtUtils jwtUtils;

    @BeforeEach
    void setUp() {
        accountService = new AccountServiceImpl(userAuthProvider, accountRepository, guestRepository, accountMapper, passwordEncoder, jwtUtils);
    }

    @Test
    void registerTest() {
        final RegisterDto dto = mock();
        final Account entity = mock();
        final Guest guest = mock();
        final RegisteredUserDto registeredUserDto = mock();

        when(dto.getRole()).thenReturn(Role.USER);
        when(dto.getPassword()).thenReturn("password");
        when(entity.getGuest()).thenReturn(guest);
        when(entity.getLogin()).thenReturn("login");
        when(entity.getRole()).thenReturn(Role.USER);
        when(passwordEncoder.encode(any())).thenReturn("encoded");
        when(accountMapper.toEntity(dto)).thenReturn(entity);
        when(accountMapper.toRegisteredUserDto(entity)).thenReturn(registeredUserDto);
        when(userAuthProvider.createToken("login", Role.USER)).thenReturn("token");

        assertEquals(registeredUserDto, accountService.register(dto, false));

        verify(entity).setPassword("encoded");
        verify(guestRepository).save(guest);
        verify(accountRepository).save(entity);
        verify(registeredUserDto).setToken("token");
    }

    @Test
    void authorizeTest() {
        final Account entity = mock();
        final AccountDto dto = mock();
        final RegisteredUserDto registeredUserDto = mock();

        when(accountRepository.findByLogin("login")).thenReturn(Optional.of(entity));
        when(dto.getLogin()).thenReturn("login");
        when(dto.getPassword()).thenReturn("password");
        when(entity.getPassword()).thenReturn("encoded");
        when(entity.getLogin()).thenReturn("login");
        when(entity.getRole()).thenReturn(Role.USER);
        when(userAuthProvider.createToken("login", Role.USER)).thenReturn("token");
        when(passwordEncoder.matches(any(), eq("encoded"))).thenReturn(true);
        when(accountMapper.toRegisteredUserDto(entity)).thenReturn(registeredUserDto);

        final Optional<RegisteredUserDto> actual = accountService.authorize(dto);

        assertTrue(actual.isPresent());
        assertEquals(registeredUserDto, actual.get());
        verify(registeredUserDto).setToken("token");
    }

    @Test
    void getAccountByTokenTest() {
        final Account entity = mock();

        when(jwtUtils.escapeToken("token")).thenReturn("escaped");
        when(userAuthProvider.getLoginFromJwt("escaped")).thenReturn("login");
        when(accountRepository.findByLogin("login")).thenReturn(Optional.of(entity));

        final Optional<Account> actual = accountService.getAccountByToken("token");

        assertTrue(actual.isPresent());
        assertEquals(entity, actual.get());
    }

    @Test
    void getAccountByIdTest() {
        final Account entity = mock();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(entity));

        final Optional<Account> actual = accountService.getAccountById(1L);

        assertTrue(actual.isPresent());
        assertEquals(entity, actual.get());
    }
}
