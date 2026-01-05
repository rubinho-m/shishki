package com.rubinho.shishki.mappers.impl;

import com.rubinho.shishki.dto.AdditionalServiceDto;
import com.rubinho.shishki.dto.BookingRequestDto;
import com.rubinho.shishki.dto.BookingResponseDto;
import com.rubinho.shishki.dto.GuestDto;
import com.rubinho.shishki.dto.ShopItemDto;
import com.rubinho.shishki.exceptions.AccountNotFoundException;
import com.rubinho.shishki.exceptions.HouseNotFoundException;
import com.rubinho.shishki.mappers.AdditionalServiceMapper;
import com.rubinho.shishki.mappers.BookingMapper;
import com.rubinho.shishki.mappers.GuestMapper;
import com.rubinho.shishki.mappers.HouseMapper;
import com.rubinho.shishki.mappers.ShopItemMapper;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.model.AdditionalService;
import com.rubinho.shishki.model.Booking;
import com.rubinho.shishki.model.Guest;
import com.rubinho.shishki.model.House;
import com.rubinho.shishki.model.ShopItem;
import com.rubinho.shishki.repository.AccountRepository;
import com.rubinho.shishki.repository.AdditionalServiceRepository;
import com.rubinho.shishki.repository.GuestRepository;
import com.rubinho.shishki.repository.HouseRepository;
import com.rubinho.shishki.repository.ShopItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class BookingMapperImpl implements BookingMapper {
    private final AccountRepository accountRepository;
    private final HouseRepository houseRepository;
    private final ShopItemRepository shopItemRepository;
    private final AdditionalServiceRepository additionalServiceRepository;
    private final GuestRepository guestRepository;
    private final HouseMapper houseMapper;
    private final GuestMapper guestMapper;
    private final ShopItemMapper shopItemMapper;
    private final AdditionalServiceMapper additionalServiceMapper;

    @Autowired
    public BookingMapperImpl(AccountRepository accountRepository,
                             HouseRepository houseRepository,
                             ShopItemRepository shopItemRepository,
                             AdditionalServiceRepository additionalServiceRepository,
                             GuestRepository guestRepository,
                             HouseMapper houseMapper,
                             GuestMapper guestMapper,
                             ShopItemMapper shopItemMapper,
                             AdditionalServiceMapper additionalServiceMapper
    ) {
        this.accountRepository = accountRepository;
        this.houseRepository = houseRepository;
        this.shopItemRepository = shopItemRepository;
        this.additionalServiceRepository = additionalServiceRepository;
        this.guestRepository = guestRepository;
        this.houseMapper = houseMapper;
        this.guestMapper = guestMapper;
        this.shopItemMapper = shopItemMapper;
        this.additionalServiceMapper = additionalServiceMapper;
    }

    @Override
    public Booking toEntity(BookingRequestDto bookingRequestDto) throws AccountNotFoundException, HouseNotFoundException {
        final Account user = accountRepository.findByLogin(bookingRequestDto.getLogin())
                .orElseThrow(
                        () -> new AccountNotFoundException(bookingRequestDto.getLogin())
                );
        final House house = houseRepository.findById(bookingRequestDto.getHouseId())
                .orElseThrow(
                        () -> new HouseNotFoundException(bookingRequestDto.getHouseId())
                );

        final Set<ShopItem> shopItems = shopItemRepository
                .findAllByIdIn(bookingRequestDto.getShopItems());
        final Set<Guest> guests = guestRepository
                .findAllByIdIn(bookingRequestDto.getGuests());
        final Set<AdditionalService> additionalServices = additionalServiceRepository
                .findAllByIdIn(bookingRequestDto.getServices());

        return Booking.builder()
                .user(user)
                .house(house)
                .dateStart(bookingRequestDto.getDateStart())
                .dateEnd(bookingRequestDto.getDateEnd())
                .guests(guests)
                .services(additionalServices)
                .shopItems(shopItems)
                .uniqueKey(null) // добавится в BookingService
                .build();
    }

    @Override
    public BookingResponseDto toDto(Booking booking) {
        final List<GuestDto> guests = booking.getGuests()
                .stream()
                .map(guestMapper::toDto)
                .toList();

        final List<ShopItemDto> shopItems = booking.getShopItems()
                .stream()
                .map(shopItemMapper::toDto)
                .toList();

        final List<AdditionalServiceDto> services = booking.getServices()
                .stream()
                .map(additionalServiceMapper::toDto)
                .toList();

        return BookingResponseDto.builder()
                .id(booking.getId())
                .login(booking.getUser().getLogin())
                .house(houseMapper.toDto(booking.getHouse()))
                .dateStart(booking.getDateStart())
                .dateEnd(booking.getDateEnd())
                .guests(guests)
                .shopItems(shopItems)
                .services(services)
                .uniqueKey(booking.getUniqueKey())
                .build();
    }
}
