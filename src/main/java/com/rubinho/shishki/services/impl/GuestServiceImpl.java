package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.dto.GuestDto;
import com.rubinho.shishki.mappers.GuestMapper;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.model.Booking;
import com.rubinho.shishki.model.Guest;
import com.rubinho.shishki.repository.GuestRepository;
import com.rubinho.shishki.services.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GuestServiceImpl implements GuestService {
    private final GuestRepository guestRepository;
    private final GuestMapper guestMapper;

    @Autowired
    public GuestServiceImpl(GuestRepository guestRepository, GuestMapper guestMapper) {
        this.guestRepository = guestRepository;
        this.guestMapper = guestMapper;
    }

    @Override
    public List<GuestDto> getAll() {
        return guestRepository.findAll()
                .stream()
                .map(guestMapper::toDto)
                .toList();
    }

    @Override
    public Set<GuestDto> getGuestsByAccount(Account account) {
        return account.getBookings()
                .stream()
                .flatMap(booking -> booking.getGuests().stream())
                .map(guestMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public GuestDto get(Long id) {
        return guestMapper.toDto(
                guestRepository.findById(id)
                        .orElseThrow(
                                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No guest by this id")
                        )
        );
    }

    @Override
    public GuestDto save(GuestDto guestDto) {
        return guestMapper.toDto(
                guestRepository.save(
                        guestMapper.toEntity(guestDto)
                )
        );
    }

    @Override
    public GuestDto edit(Long id, GuestDto guestDto) {
        guestDto.setId(id);
        return save(guestDto);
    }

    @Override
    public void delete(Long id) {
        final Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No guest by this id"));
        guestRepository.delete(guest);
    }
}
