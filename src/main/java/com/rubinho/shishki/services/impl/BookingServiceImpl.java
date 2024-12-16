package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.dto.BookingRequestDto;
import com.rubinho.shishki.dto.BookingResponseDto;
import com.rubinho.shishki.mappers.BookingMapper;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.model.Booking;
import com.rubinho.shishki.model.Glamping;
import com.rubinho.shishki.model.House;
import com.rubinho.shishki.model.Role;
import com.rubinho.shishki.repository.BookingRepository;
import com.rubinho.shishki.repository.GlampingRepository;
import com.rubinho.shishki.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
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
        final Glamping glamping = glampingRepository.findById(glampingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No glamping with id %s".formatted(glampingId)));
        if (!glamping.getOwner().equals(account)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of this glamping");
        }
        return getAll()
                .stream()
                .filter(booking -> booking.getHouse().getGlampingId().equals(glampingId))
                .toList();
    }

    @Override
    public BookingResponseDto get(Long id) {
        final Booking booking = bookingRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No booking with id %d".formatted(id))
                );

        return bookingMapper.toDto(booking);
    }

    @Override
    public BookingResponseDto save(BookingRequestDto bookingRequestDto, Account account) {
        final Booking booking = bookingMapper.toEntity(bookingRequestDto);
        check(booking, account);
        checkHouse(booking);
        checkDates(booking);
        final Integer uniqueKey = ThreadLocalRandom.current().nextInt(MIN_UNIQUE_KEY_VALUE, MAX_UNIQUE_KEY_VALUE + 1);
        booking.setUniqueKey(uniqueKey);
        return bookingMapper.toDto(
                bookingRepository.save(booking)
        );
    }

    @Override
    public BookingResponseDto edit(Long id, BookingRequestDto bookingRequestDto, Account account) {
        bookingRequestDto.setId(id);
        return save(bookingRequestDto, account);
    }

    @Override
    public void delete(Long id, Account account) {
        final Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No booking by this id"));
        check(booking, account);
        bookingRepository.delete(booking);
    }

    private void checkHouse(Booking booking) {
        final House house = booking.getHouse();
        if (!house.getStatus().getStatus().equals("ALLOWED")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "House is not allowed");
        }
    }

    private void checkDates(Booking booking) {
        final LocalDate start = booking.getDateStart();
        final LocalDate end = booking.getDateEnd();
        final List<Booking> houseBookings = booking.getHouse().getBookings();
        for (Booking houseBooking : houseBookings) {
            final LocalDate existingStart = houseBooking.getDateStart();
            final LocalDate existingEnd = houseBooking.getDateEnd();

            if (start.isBefore(existingStart) && end.isAfter(existingStart)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Crossing date");
            }
            if (start.isAfter(existingStart) && start.isBefore(existingEnd) && end.isAfter(existingStart) && end.isBefore(existingEnd)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Crossing date");
            }
            if (start.isBefore(existingEnd) && end.isAfter(existingEnd)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Crossing date");
            }
        }
    }

    private void check(Booking booking, Account account) {
        if (account.getRole().equals(Role.ADMIN)) {
            return;
        }
        if (!booking.getUser().equals(account)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to access this booking");
        }
    }
}
