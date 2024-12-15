package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.dto.PotentialOwnerDto;
import com.rubinho.shishki.mappers.AccountMapper;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.model.Role;
import com.rubinho.shishki.repository.AccountRepository;
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

    @Autowired
    public AdminServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public List<PotentialOwnerDto> getAllPotentialOwners() {
        return accountRepository.findAllByRole(Role.POTENTIAL_OWNER)
                .stream()
                .map(accountMapper::toPotentialOwnerDto)
                .toList();
    }

    @Override
    public void setNewRole(Long id, Role role) {
        final Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        account.setRole(role);
        accountRepository.save(account);
    }
}
