package com.rubinho.shishki.services;

import com.rubinho.shishki.dto.HouseDto;
import com.rubinho.shishki.filters.HouseFilter;
import com.rubinho.shishki.model.Account;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface HouseService {
    List<HouseDto> getAll(HouseFilter houseFilter);

    HouseDto get(Long id);

    Set<LocalDate> getBookedDates(Long id);

    String getCode(Long id, Account account);

    HouseDto save(HouseDto houseDto, Account account);

    HouseDto edit(Long id, HouseDto houseDto, Account account);

    void delete(Long id, Account account);
}
