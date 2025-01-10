package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.dto.GlampingResponseDto;
import com.rubinho.shishki.dto.PotentialOwnerDto;
import com.rubinho.shishki.dto.SecuredAccountDto;
import com.rubinho.shishki.mappers.AccountMapper;
import com.rubinho.shishki.mappers.GlampingMapper;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.model.Glamping;
import com.rubinho.shishki.model.GlampingStatus;
import com.rubinho.shishki.model.Role;
import com.rubinho.shishki.repository.AccountRepository;
import com.rubinho.shishki.repository.GlampingRepository;
import com.rubinho.shishki.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    final AccountRepository accountRepository;
    final AccountMapper accountMapper;
    final GlampingRepository glampingRepository;
    final GlampingMapper glampingMapper;

    @Autowired
    public AdminServiceImpl(AccountRepository accountRepository,
                            GlampingRepository glampingRepository,
                            AccountMapper accountMapper,
                            GlampingMapper glampingMapper) {
        this.accountRepository = accountRepository;
        this.glampingRepository = glampingRepository;
        this.accountMapper = accountMapper;
        this.glampingMapper = glampingMapper;
    }

    @Override
    public List<PotentialOwnerDto> getAllPotentialOwners() {
        return accountRepository.findAllByRole(Role.POTENTIAL_OWNER)
                .stream()
                .map(accountMapper::toPotentialOwnerDto)
                .toList();
    }

    @Override
    public List<SecuredAccountDto> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(accountMapper::toSecuredAccountDto)
                .toList();
    }

    @Override
    public List<GlampingResponseDto> getAllGlampingsForReview() {
        return glampingRepository.findAllByGlampingStatus(GlampingStatus.WAITING_FOR_CONFIRMATION)
                .stream()
                .map(glampingMapper::toDto)
                .toList();
    }

    @Override
    public void setNewGlampingStatus(Long id, GlampingStatus glampingStatus) {
        final Glamping glamping = glampingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Glamping not found"));
        glamping.setGlampingStatus(glampingStatus);
        glampingRepository.save(glamping);
    }

    @Override
    public void setNewRole(Long id, Role role) {
        final Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        account.setRole(role);
        accountRepository.save(account);
    }
}
