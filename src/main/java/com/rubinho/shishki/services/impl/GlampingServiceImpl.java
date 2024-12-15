package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.dto.GlampingDto;
import com.rubinho.shishki.mappers.GlampingMapper;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.model.Glamping;
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
    public List<GlampingDto> getAll() {
        return glampingRepository.findAll()
                .stream()
                .map(glampingMapper::toDto)
                .toList();
    }

    @Override
    public GlampingDto get(Long id) {
        return glampingMapper.toDto(
                glampingRepository.findById(id)
                        .orElseThrow(
                                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No glamping by this id")
                        )
        );
    }

    @Override
    public GlampingDto save(GlampingDto glampingDto, Account account) {
        final Glamping glamping = glampingMapper.toEntity(glampingDto);
        check(glamping, account);
        return glampingMapper.toDto(
                glampingRepository.save(glamping)
        );
    }

    @Override
    public GlampingDto edit(Long id, GlampingDto glampingDto, Account account) {
        glampingDto.setId(id);
        return save(glampingDto, account);
    }

    @Override
    public void delete(Long id, Account account) {
        final Glamping glamping = glampingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No glamping by this id"));
        check(glamping, account);
        glampingRepository.delete(glamping);
    }

    void check(Glamping glamping, Account account) {
        if (account.getRole().equals(Role.ADMIN)){
            return;
        }
        if (!glamping.getOwner().equals(account)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of the glamping");
        }
    }
}
