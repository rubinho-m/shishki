package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.dto.HouseDto;
import com.rubinho.shishki.exceptions.rest.ForbiddenException;
import com.rubinho.shishki.filters.HouseFilter;
import com.rubinho.shishki.mappers.HouseMapper;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.model.Booking;
import com.rubinho.shishki.model.House;
import com.rubinho.shishki.model.Role;
import com.rubinho.shishki.repository.HouseRepository;
import com.rubinho.shishki.services.HouseService;
import com.rubinho.shishki.services.HouseSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class HouseServiceImpl implements HouseService {
    private final HouseRepository houseRepository;
    private final HouseMapper houseMapper;
    private final HouseSpecificationService houseSpecificationService;

    @Autowired
    public HouseServiceImpl(HouseRepository houseRepository,
                            HouseMapper houseMapper,
                            HouseSpecificationService houseSpecificationService) {
        this.houseRepository = houseRepository;
        this.houseMapper = houseMapper;
        this.houseSpecificationService = houseSpecificationService;
    }

    @Override
    public List<HouseDto> getAll(HouseFilter houseFilter) {
        final Specification<House> specification = houseSpecificationService.filterBy(houseFilter);
        return houseRepository.findAll(specification)
                .stream()
                .map(houseMapper::toDto)
                .toList();
    }

    @Override
    public Optional<HouseDto> get(Long id) {
        return houseRepository.findById(id).map(houseMapper::toDto);
    }

    @Override
    public Optional<Set<LocalDate>> getBookedDates(Long id) {
        return houseRepository.findById(id)
                .map(house -> house.getBookings().stream()
                        .flatMap(booking -> StreamSupport.stream(
                                Spliterators.spliteratorUnknownSize(
                                        new DateRangeIterator(booking.getDateStart(), booking.getDateEnd()),
                                        Spliterator.ORDERED
                                ), false
                        ))
                        .collect(Collectors.toSet()));
    }

    @Override
    public Optional<String> getCode(Long id, Account account) {
        return switch (account.getRole()) {
            case USER, POTENTIAL_OWNER -> throw new ForbiddenException("You are not allowed to access this resource");
            case ADMIN, STAFF -> houseRepository.findById(id).map(this::getActualCode);
            case OWNER -> houseRepository.findById(id)
                    .map(house -> {
                        if (!house.getGlamping().getOwner().equals(account)) {
                            throw new ForbiddenException("You are not allowed to access this resource");
                        }
                        return getActualCode(house);
                    });
        };
    }

    @Override
    public HouseDto save(HouseDto houseDto, Account account) {
        final House house = houseMapper.toEntity(houseDto);
        check(house, account);
        return houseMapper.toDto(houseRepository.save(house));
    }

    @Override
    public Optional<HouseDto> edit(Long id, HouseDto houseDto, Account account) {
        if (!houseRepository.existsById(id)) {
            return Optional.empty();
        }
        houseDto.setId(id);
        return Optional.of(save(houseDto, account));
    }

    @Override
    public void delete(Long id, Account account) {
        houseRepository.findById(id).ifPresent(
                house -> {
                    check(house, account);
                    houseRepository.delete(house);
                }
        );
    }

    private static class DateRangeIterator implements Iterator<LocalDate> {
        private LocalDate current;
        private final LocalDate end;

        public DateRangeIterator(LocalDate start, LocalDate end) {
            this.current = start;
            this.end = end;
        }

        @Override
        public boolean hasNext() {
            return current.isBefore(end) || current.isEqual(end);
        }

        @Override
        public LocalDate next() {
            LocalDate nextDate = current;
            current = current.plusDays(1);
            return nextDate;
        }
    }

    private void check(House house, Account account) {
        if (account.getRole().equals(Role.ADMIN)) {
            return;
        }
        if (!house.getGlamping().getOwner().equals(account)) {
            throw new ForbiddenException("You have not permission to operate this glamping");
        }
    }

    private String getActualCode(House house) {
        final List<Booking> bookings = house.getBookings();
        for (Booking booking : bookings) {
            final LocalDate startDate = booking.getDateStart();
            final LocalDate endDate = booking.getDateEnd();
            if (isNowInRange(startDate, endDate)) {
                return String.valueOf(booking.getUniqueKey());
            }
        }
        return "Нет бронирований на сегодняшний день";
    }

    private boolean isNowInRange(LocalDate startDate, LocalDate endDate) {
        LocalDate now = LocalDate.now();
        return !now.isBefore(startDate) && !now.isAfter(endDate);
    }
}
