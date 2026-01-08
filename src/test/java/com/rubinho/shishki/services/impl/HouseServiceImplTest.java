package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.dto.HouseDto;
import com.rubinho.shishki.exceptions.GlampingNotFoundException;
import com.rubinho.shishki.exceptions.HouseStatusNotFoundException;
import com.rubinho.shishki.exceptions.HouseTypeNotFoundException;
import com.rubinho.shishki.exceptions.rest.ForbiddenException;
import com.rubinho.shishki.filters.HouseFilter;
import com.rubinho.shishki.mappers.HouseMapper;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.model.Booking;
import com.rubinho.shishki.model.Glamping;
import com.rubinho.shishki.model.House;
import com.rubinho.shishki.model.Role;
import com.rubinho.shishki.repository.HouseRepository;
import com.rubinho.shishki.services.HouseService;
import com.rubinho.shishki.services.HouseSpecificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HouseServiceImplTest {
    private HouseService houseService;

    @Mock
    private HouseRepository houseRepository;
    @Mock
    private HouseMapper houseMapper;
    @Mock
    private HouseSpecificationService houseSpecificationService;

    @BeforeEach
    void init() {
        houseService = new HouseServiceImpl(houseRepository, houseMapper, houseSpecificationService);
    }

    @Test
    void getAllTest() {
        final HouseFilter houseFilter = mock();
        final Specification<House> specification = mock();
        final House entity1 = mock();
        final House entity2 = mock();
        final HouseDto dto1 = mock();
        final HouseDto dto2 = mock();

        when(houseSpecificationService.filterBy(houseFilter)).thenReturn(specification);
        when(houseMapper.toDto(entity1)).thenReturn(dto1);
        when(houseMapper.toDto(entity2)).thenReturn(dto2);
        when(houseRepository.findAll(specification)).thenReturn(List.of(entity1, entity2));

        assertThat(houseService.getAll(houseFilter))
                .hasSize(2)
                .contains(dto1, dto2);
    }

    @Test
    void getTest() {
        final House entity = mock();
        final HouseDto dto = mock();

        when(houseRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(houseMapper.toDto(entity)).thenReturn(dto);

        final Optional<HouseDto> actual = houseService.get(1L);

        assertTrue(actual.isPresent());
        assertEquals(dto, actual.get());
    }

    @Test
    void getBookedDatesTest() {
        final House house = mock();
        final Booking booking = mock();
        final LocalDate start = LocalDate.of(2026, 1, 1);
        final LocalDate end = LocalDate.of(2026, 1, 2);

        when(houseRepository.findById(1L)).thenReturn(Optional.of(house));
        when(booking.getDateStart()).thenReturn(start);
        when(booking.getDateEnd()).thenReturn(end);
        when(house.getBookings()).thenReturn(List.of(booking));

        Optional<Set<LocalDate>> bookedDates = houseService.getBookedDates(1L);
        assertTrue(bookedDates.isPresent());
        assertThat(bookedDates.get())
                .hasSize(2)
                .contains(start, end);
    }

    @EnumSource(Role.class)
    @ParameterizedTest
    void getCodeTest(Role role) {
        final Account account = mock();
        final Set<Role> valid = Set.of(Role.ADMIN, Role.STAFF, Role.OWNER);
        final Set<Role> forbidden = Set.of(Role.USER, Role.POTENTIAL_OWNER);
        when(account.getRole()).thenReturn(role);
        if (valid.contains(role)) {
            final House house = mock();
            final Booking booking = mock();
            when(booking.getDateStart()).thenReturn(LocalDate.now().minusDays(1));
            when(booking.getDateEnd()).thenReturn(LocalDate.now().plusDays(1));
            when(booking.getUniqueKey()).thenReturn(1234);
            when(house.getBookings()).thenReturn(List.of(booking));
            if (Role.OWNER.equals(role)) {
                final Glamping glamping = mock();
                when(glamping.getOwner()).thenReturn(account);
                when(house.getGlamping()).thenReturn(glamping);
            }
            when(houseRepository.findById(1L)).thenReturn(Optional.of(house));

            Optional<String> actual = houseService.getCode(1L, account);

            assertTrue(actual.isPresent());
            assertEquals("1234", actual.get());
        } else if (forbidden.contains(role)) {
            assertThrows(ForbiddenException.class, () -> houseService.getCode(1L, account));
        }
    }

    @Test
    void saveTest() throws HouseTypeNotFoundException, HouseStatusNotFoundException, GlampingNotFoundException {
        final Account account = mock();
        final House entity = mock();
        final HouseDto dto = mock();

        when(account.getRole()).thenReturn(Role.ADMIN);
        when(houseMapper.toEntity(dto)).thenReturn(entity);
        when(houseRepository.save(entity)).thenReturn(entity);
        when(houseMapper.toDto(entity)).thenReturn(dto);

        assertEquals(dto, houseService.save(dto, account));
    }

    @Test
    void editTest() throws HouseTypeNotFoundException, HouseStatusNotFoundException, GlampingNotFoundException {
        final Account account = mock();
        final House entity = mock();
        final HouseDto dto = mock();

        when(houseRepository.existsById(1L)).thenReturn(true);
        when(account.getRole()).thenReturn(Role.ADMIN);
        when(houseMapper.toEntity(dto)).thenReturn(entity);
        when(houseRepository.save(entity)).thenReturn(entity);
        when(houseMapper.toDto(entity)).thenReturn(dto);

        Optional<HouseDto> actual = houseService.edit(1L, dto, account);
        assertTrue(actual.isPresent());
        assertEquals(dto, actual.get());

        verify(dto).setId(1L);
    }

    @Test
    void deleteTest() {
        final House entity = mock();
        final Account account = mock();
        when(account.getRole()).thenReturn(Role.ADMIN);

        when(houseRepository.findById(1L)).thenReturn(Optional.of(entity));

        houseService.delete(1L, account);

        verify(houseRepository).delete(entity);
    }
}
