package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.dto.GlampingRequestDto;
import com.rubinho.shishki.dto.GlampingResponseDto;
import com.rubinho.shishki.mappers.GlampingMapper;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.model.Glamping;
import com.rubinho.shishki.model.GlampingStatus;
import com.rubinho.shishki.model.Role;
import com.rubinho.shishki.repository.GlampingRepository;
import com.rubinho.shishki.services.GlampingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class GlampingServiceImpl implements GlampingService {
    private final GlampingRepository glampingRepository;
    private final GlampingMapper glampingMapper;

    @Autowired
    public GlampingServiceImpl(GlampingRepository glampingRepository,
                               GlampingMapper glampingMapper) {
        this.glampingRepository = glampingRepository;
        this.glampingMapper = glampingMapper;
    }

    @Override
    public List<GlampingResponseDto> getAll() {
        return glampingRepository.findAll()
                .stream()
                .map(glampingMapper::toDto)
                .toList();
    }

    @Override
    public List<GlampingResponseDto> getAllApproved() {
        return glampingRepository.findAllByGlampingStatus(GlampingStatus.APPROVED)
                .stream()
                .map(glampingMapper::toDto)
                .toList();
    }

    @Override
    public GlampingResponseDto get(Long id) {
        return glampingMapper.toDto(
                glampingRepository.findById(id)
                        .orElseThrow(
                                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No glamping by this id")
                        )
        );
    }

    @Override
    public GlampingResponseDto save(GlampingRequestDto glampingRequestDto, Account account) {
        final Glamping glamping = glampingMapper.toEntity(glampingRequestDto);
        glamping.setGlampingStatus(GlampingStatus.WAITING_FOR_CONFIRMATION);
        check(glamping, account);
        return glampingMapper.toDto(
                glampingRepository.save(glamping)
        );
    }

    @Override
    public GlampingResponseDto edit(Long id, GlampingRequestDto glampingRequestDto, Account account) {
        glampingRequestDto.setId(id);
        final Glamping glamping = glampingMapper.toEntity(glampingRequestDto);
        check(glamping, account);
        return glampingMapper.toDto(
                glampingRepository.save(glamping)
        );
    }

    @Override
    public void delete(Long id, Account account) {
        final Glamping glamping = glampingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No glamping by this id"));
        check(glamping, account);
        glampingRepository.delete(glamping);
    }

    void check(Glamping glamping, Account account) {
        if (account.getRole().equals(Role.ADMIN)) {
            return;
        }
        if (!glamping.getOwner().equals(account)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of the glamping");
        }
    }
}
