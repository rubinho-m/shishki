package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.dto.GuestDto;
import com.rubinho.shishki.mappers.GuestMapper;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.model.Booking;
import com.rubinho.shishki.model.Guest;
import com.rubinho.shishki.repository.GuestRepository;
import com.rubinho.shishki.services.GuestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GuestServiceImplTest {
    private GuestService guestService;

    @Mock
    private GuestRepository guestRepository;

    @Mock
    private GuestMapper guestMapper;

    @BeforeEach
    void setUp() {
        guestService = new GuestServiceImpl(guestRepository, guestMapper);
    }

    @Test
    void getAllTest(){
        final Guest entity1 = mock();
        final Guest entity2 = mock();
        final GuestDto dto1 = mock();
        final GuestDto dto2 = mock();

        when(guestRepository.findAll()).thenReturn(List.of(entity1, entity2));
        when(guestMapper.toDto(entity1)).thenReturn(dto1);
        when(guestMapper.toDto(entity2)).thenReturn(dto2);

        assertThat(guestService.getAll())
                .hasSize(2)
                .contains(dto1, dto2);
    }

    @Test
    void getGuestsByAccountTest() {
        final Account account = mock();
        final Booking booking = mock();
        final Guest entity = mock();
        final GuestDto dto = mock();

        when(guestMapper.toDto(entity)).thenReturn(dto);
        when(booking.getGuests()).thenReturn(Set.of(entity));
        when(account.getBookings()).thenReturn(List.of(booking));

        assertThat(guestService.getGuestsByAccount(account))
                .hasSize(1)
                .contains(dto);
    }

    @Test
    void getTest() {
        final Guest entity = mock();
        final GuestDto dto = mock();

        when(guestRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(guestMapper.toDto(entity)).thenReturn(dto);

        final Optional<GuestDto> actual = guestService.get(1L);

        assertTrue(actual.isPresent());
        assertEquals(dto, actual.get());
    }

    @Test
    void saveTest() {
        final Guest entity = mock();
        final GuestDto dto = mock();

        when(guestMapper.toEntity(dto)).thenReturn(entity);
        when(guestRepository.save(entity)).thenReturn(entity);
        when(guestMapper.toDto(entity)).thenReturn(dto);

        assertEquals(dto, guestService.save(dto));
    }

    @Test
    void editTest() {
        final Guest entity = mock();
        final GuestDto dto = mock();

        when(guestRepository.existsById(1L)).thenReturn(true);
        when(guestMapper.toEntity(dto)).thenReturn(entity);
        when(guestRepository.save(entity)).thenReturn(entity);
        when(guestMapper.toDto(entity)).thenReturn(dto);

        final Optional<GuestDto> actual = guestService.edit(1L, dto);

        assertTrue(actual.isPresent());
        assertEquals(dto, actual.get());
    }

    @Test
    void deleteTest() {
        final Guest entity = mock();

        when(guestRepository.findById(1L)).thenReturn(Optional.of(entity));

        guestService.delete(1L);

        verify(guestRepository).delete(entity);
    }
}
