package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.dto.BookingRequestDto;
import com.rubinho.shishki.dto.BookingResponseDto;
import com.rubinho.shishki.exceptions.AccountNotFoundException;
import com.rubinho.shishki.exceptions.HouseNotFoundException;
import com.rubinho.shishki.exceptions.rest.BookingValidationException;
import com.rubinho.shishki.exceptions.rest.ForbiddenException;
import com.rubinho.shishki.mappers.BookingMapper;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.model.Booking;
import com.rubinho.shishki.model.House;
import com.rubinho.shishki.model.Role;
import com.rubinho.shishki.repository.BookingRepository;
import com.rubinho.shishki.repository.GlampingRepository;
import com.rubinho.shishki.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final GlampingRepository glampingRepository;
    private final BookingMapper bookingMapper;

    private final static int MIN_UNIQUE_KEY_VALUE = 1000;
    private final static int MAX_UNIQUE_KEY_VALUE = 9999;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository,
                              BookingMapper bookingMapper,
                              GlampingRepository glampingRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
        this.glampingRepository = glampingRepository;
    }

    @Override
    public List<BookingResponseDto> getAll() {
        return bookingRepository.findAll()
                .stream()
                .map(bookingMapper::toDto)
                .toList();
    }

    @Override
    public List<BookingResponseDto> getAllByAccount(Account account) {
        return bookingRepository.findAllByUser(account)
                .stream()
                .map(bookingMapper::toDto)
                .toList();
    }

    @Override
    public List<BookingResponseDto> getAllByGlamping(Long glampingId, Account account) {
        return glampingRepository.findById(glampingId)
                .map(glamping -> {
                    if (!account.equals(glamping.getOwner()) && !Role.ADMIN.equals(account.getRole())) {
                        throw new ForbiddenException("You are not the owner of this glamping");
                    }
                    return getAll()
                            .stream()
                            .filter(booking -> booking.getHouse().getGlampingId().equals(glampingId))
                            .toList();
                })
                .orElse(Collections.emptyList());
    }

    @Override
    public Optional<BookingResponseDto> get(Long id) {
        return bookingRepository.findById(id).map(bookingMapper::toDto);
    }

    @Override
    public BookingResponseDto save(BookingRequestDto bookingRequestDto, Account account) throws BookingValidationException, HouseNotFoundException, AccountNotFoundException {
        final Booking booking = bookingMapper.toEntity(bookingRequestDto);
        check(booking, account);
        checkHouse(booking);
        checkDates(booking);
        final Integer uniqueKey = ThreadLocalRandom.current().nextInt(MIN_UNIQUE_KEY_VALUE, MAX_UNIQUE_KEY_VALUE + 1);
        booking.setUniqueKey(uniqueKey);
        return bookingMapper.toDto(bookingRepository.save(booking));
    }

    @Override
    public Optional<BookingResponseDto> edit(Long id, BookingRequestDto bookingRequestDto, Account account) throws BookingValidationException, HouseNotFoundException, AccountNotFoundException {
        if (!bookingRepository.existsById(id)) {
            return Optional.empty();
        }
        bookingRequestDto.setId(id);
        return Optional.of(save(bookingRequestDto, account));
    }

    @Override
    public void delete(Long id, Account account) {
        bookingRepository.findById(id).ifPresent(booking -> {
            check(booking, account);
            bookingRepository.delete(booking);
        });
    }

    private void checkHouse(Booking booking) throws BookingValidationException {
        final House house = booking.getHouse();
        if (!house.getStatus().getStatus().equals("ALLOWED")) {
            throw new BookingValidationException("House is not allowed");
        }
    }

    private void checkDates(Booking booking) throws BookingValidationException {
        final LocalDate start = booking.getDateStart();
        final LocalDate end = booking.getDateEnd();
        final List<Booking> houseBookings = booking.getHouse().getBookings();
        for (Booking houseBooking : houseBookings) {
            final LocalDate existingStart = houseBooking.getDateStart();
            final LocalDate existingEnd = houseBooking.getDateEnd();

            if (start.isBefore(existingStart) && end.isAfter(existingStart)) {
                throw new BookingValidationException("Crossing date");
            }
            if (start.isAfter(existingStart) && start.isBefore(existingEnd) && end.isAfter(existingStart) && end.isBefore(existingEnd)) {
                throw new BookingValidationException("Crossing date");
            }
            if (start.isBefore(existingEnd) && end.isAfter(existingEnd)) {
                throw new BookingValidationException("Crossing date");
            }
        }
    }

    private void check(Booking booking, Account account) {
        if (account.getRole().equals(Role.ADMIN)) {
            return;
        }
        if (!booking.getUser().equals(account)) {
            throw new ForbiddenException("You are not allowed to access this booking");
        }
    }
}