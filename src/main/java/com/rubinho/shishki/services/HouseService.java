package com.rubinho.shishki.services;

import com.rubinho.shishki.dto.HouseDto;
import com.rubinho.shishki.exceptions.GlampingNotFoundException;
import com.rubinho.shishki.exceptions.HouseStatusNotFoundException;
import com.rubinho.shishki.exceptions.HouseTypeNotFoundException;
import com.rubinho.shishki.filters.HouseFilter;
import com.rubinho.shishki.model.Account;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface HouseService {
    List<HouseDto> getAll(HouseFilter houseFilter);

    Optional<HouseDto> get(Long id);

    Optional<Set<LocalDate>> getBookedDates(Long id);

    Optional<String> getCode(Long id, Account account);

    HouseDto save(HouseDto houseDto, Account account) throws HouseTypeNotFoundException, HouseStatusNotFoundException, GlampingNotFoundException;

    Optional<HouseDto> edit(Long id, HouseDto houseDto, Account account) throws HouseTypeNotFoundException, HouseStatusNotFoundException, GlampingNotFoundException;

    void delete(Long id, Account account);
}
