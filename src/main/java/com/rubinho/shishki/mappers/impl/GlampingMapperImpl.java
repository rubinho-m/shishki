package com.rubinho.shishki.mappers.impl;

import com.rubinho.shishki.dto.GlampingDto;
import com.rubinho.shishki.mappers.GlampingMapper;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.model.Glamping;
import com.rubinho.shishki.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class GlampingMapperImpl implements GlampingMapper {
    private final AccountRepository accountRepository;

    @Autowired
    public GlampingMapperImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Glamping toEntity(GlampingDto glampingDto) {
        final Account account = accountRepository.findByLogin(glampingDto.getOwnerLogin())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        return Glamping.builder()
                .id(glampingDto.getId())
                .description(glampingDto.getDescription())
                .address(glampingDto.getAddress())
                .owner(account)
                .photoName(glampingDto.getPhotoName())
                .build();
    }

    @Override
    public GlampingDto toDto(Glamping glamping) {
        return GlampingDto.builder()
                .id(glamping.getId())
                .description(glamping.getDescription())
                .address(glamping.getAddress())
                .ownerLogin(glamping.getOwner().getLogin())
                .photoName(glamping.getPhotoName())
                .build();
    }
}
