package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.dto.BookingRequestDto;
import com.rubinho.shishki.dto.BookingResponseDto;
import com.rubinho.shishki.dto.HouseDto;
import com.rubinho.shishki.exceptions.AccountNotFoundException;
import com.rubinho.shishki.exceptions.HouseNotFoundException;
import com.rubinho.shishki.exceptions.rest.BookingValidationException;
import com.rubinho.shishki.mappers.BookingMapper;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.model.Booking;
import com.rubinho.shishki.model.Glamping;
import com.rubinho.shishki.model.House;
import com.rubinho.shishki.model.HouseStatus;
import com.rubinho.shishki.model.Role;
import com.rubinho.shishki.repository.BookingRepository;
import com.rubinho.shishki.repository.GlampingRepository;
import com.rubinho.shishki.services.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingServiceImplTest {
    private BookingService bookingService;

    @Mock
    private BookingMapper bookingMapper;
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private GlampingRepository glampingRepository;

    @BeforeEach
    void setUp() {
        bookingService = new BookingServiceImpl(bookingRepository, bookingMapper, glampingRepository);
    }

    @Test
    void getAllTest() {
        final Booking entity1 = mock();
        final Booking entity2 = mock();
        final BookingResponseDto dto1 = mock();
        final BookingResponseDto dto2 = mock();

        when(bookingRepository.findAll()).thenReturn(List.of(entity1, entity2));
        when(bookingMapper.toDto(entity1)).thenReturn(dto1);
        when(bookingMapper.toDto(entity2)).thenReturn(dto2);

        assertThat(bookingService.getAll())
                .hasSize(2)
                .contains(dto1, dto2);
    }

    @Test
    void getAllByAccountTest() {
        final Account account = mock();
        final Booking entity1 = mock();
        final Booking entity2 = mock();
        final BookingResponseDto dto1 = mock();
        final BookingResponseDto dto2 = mock();

        when(bookingRepository.findAllByUser(account)).thenReturn(List.of(entity1, entity2));
        when(bookingMapper.toDto(entity1)).thenReturn(dto1);
        when(bookingMapper.toDto(entity2)).thenReturn(dto2);

        assertThat(bookingService.getAllByAccount(account))
                .hasSize(2)
                .contains(dto1, dto2);
    }

    @Test
    void getAllByGlampingTest() {
        final Glamping glamping = mock();
        final Account account = mock();
        final HouseDto house = mock();
        final Booking entity = mock();
        final BookingResponseDto dto = mock();

        when(dto.getHouse()).thenReturn(house);
        when(house.getGlampingId()).thenReturn(1L);
        when(bookingRepository.findAll()).thenReturn(List.of(entity));
        when(bookingMapper.toDto(entity)).thenReturn(dto);
        when(account.getRole()).thenReturn(Role.ADMIN);
        when(glampingRepository.findById(1L)).thenReturn(Optional.of(glamping));

        assertThat(bookingService.getAllByGlamping(1L, account))
                .hasSize(1)
                .contains(dto);
    }

    @Test
    void getTest() {
        final Booking entity = mock();
        final BookingResponseDto responseDto = mock();

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(bookingMapper.toDto(entity)).thenReturn(responseDto);

        final Optional<BookingResponseDto> actual = bookingService.get(1L);

        assertTrue(actual.isPresent());
        assertEquals(responseDto, actual.get());
    }

    @Test
    void saveTest() throws HouseNotFoundException, AccountNotFoundException, BookingValidationException {
        final Booking entity = mock();
        final BookingRequestDto requestDto = mock();
        final BookingResponseDto responseDto = mock();
        final Account account = mock();
        final House house = mock();
        final HouseStatus houseStatus = mock();

        when(house.getBookings()).thenReturn(Collections.emptyList());
        when(houseStatus.getStatus()).thenReturn("ALLOWED");
        when(house.getStatus()).thenReturn(houseStatus);
        when(entity.getHouse()).thenReturn(house);
        when(entity.getDateStart()).thenReturn(LocalDate.now().plusDays(1));
        when(entity.getDateEnd()).thenReturn(LocalDate.now().plusDays(2));
        when(account.getRole()).thenReturn(Role.ADMIN);
        when(bookingMapper.toEntity(requestDto)).thenReturn(entity);
        when(bookingRepository.save(entity)).thenReturn(entity);
        when(bookingMapper.toDto(entity)).thenReturn(responseDto);

        assertEquals(responseDto, bookingService.save(requestDto, account));

        verify(entity).setUniqueKey(anyInt());
    }

    @Test
    void editTest() throws HouseNotFoundException, AccountNotFoundException, BookingValidationException {
        final Booking entity = mock();
        final BookingRequestDto requestDto = mock();
        final BookingResponseDto responseDto = mock();
        final Account account = mock();
        final House house = mock();
        final HouseStatus houseStatus = mock();

        when(house.getBookings()).thenReturn(Collections.emptyList());
        when(houseStatus.getStatus()).thenReturn("ALLOWED");
        when(house.getStatus()).thenReturn(houseStatus);
        when(entity.getHouse()).thenReturn(house);
        when(entity.getDateStart()).thenReturn(LocalDate.now().plusDays(1));
        when(entity.getDateEnd()).thenReturn(LocalDate.now().plusDays(2));
        when(account.getRole()).thenReturn(Role.ADMIN);
        when(bookingMapper.toEntity(requestDto)).thenReturn(entity);
        when(bookingRepository.save(entity)).thenReturn(entity);
        when(bookingMapper.toDto(entity)).thenReturn(responseDto);
        when(bookingRepository.existsById(1L)).thenReturn(true);

        final Optional<BookingResponseDto> actual = bookingService.edit(1L, requestDto, account);

        assertTrue(actual.isPresent());
        assertEquals(responseDto, actual.get());
    }

    @Test
    void deleteTest() {
        final Booking entity = mock();
        final Account account = mock();

        when(account.getRole()).thenReturn(Role.ADMIN);
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(entity));

        bookingService.delete(1L, account);

        verify(bookingRepository).delete(entity);
    }
}
