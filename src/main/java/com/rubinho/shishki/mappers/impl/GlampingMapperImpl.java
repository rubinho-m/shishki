package com.rubinho.shishki.mappers.impl;

import com.rubinho.shishki.dto.GlampingRequestDto;
import com.rubinho.shishki.dto.GlampingResponseDto;
import com.rubinho.shishki.exceptions.AccountNotFoundException;
import com.rubinho.shishki.mappers.GlampingMapper;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.model.Glamping;
import com.rubinho.shishki.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GlampingMapperImpl implements GlampingMapper {
    private final AccountRepository accountRepository;

    @Autowired
    public GlampingMapperImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Glamping toEntity(GlampingRequestDto glampingRequestDto) throws AccountNotFoundException {
        final Account account = accountRepository.findByLogin(glampingRequestDto.getOwnerLogin())
                .orElseThrow(() -> new AccountNotFoundException(glampingRequestDto.getOwnerLogin()));
        return Glamping.builder()
                .description(glampingRequestDto.getDescription())
                .address(glampingRequestDto.getAddress())
                .owner(account)
                .photoName(glampingRequestDto.getPhotoName())
                .build();
    }

    @Override
    public GlampingResponseDto toDto(Glamping glamping) {
        return GlampingResponseDto.builder()
                .id(glamping.getId())
                .description(glamping.getDescription())
                .address(glamping.getAddress())
                .ownerLogin(glamping.getOwner().getLogin())
                .photoName(glamping.getPhotoName())
                .status(glamping.getGlampingStatus())
                .build();
    }
}
