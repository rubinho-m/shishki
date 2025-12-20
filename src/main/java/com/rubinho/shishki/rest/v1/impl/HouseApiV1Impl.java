package com.rubinho.shishki.rest.v1.impl;

import com.rubinho.shishki.dto.HouseDto;
import com.rubinho.shishki.filters.HouseFilter;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.model.Glamping;
import com.rubinho.shishki.model.HouseStatus;
import com.rubinho.shishki.model.HouseType;
import com.rubinho.shishki.rest.v1.HouseApiV1;
import com.rubinho.shishki.services.AccountService;
import com.rubinho.shishki.services.HouseService;
import com.rubinho.shishki.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
public class HouseApiV1Impl implements HouseApiV1 {
    private final HouseService houseService;
    private final AccountService accountService;
    private final PhotoService photoService;

    @Autowired
    public HouseApiV1Impl(HouseService houseService,
                          AccountService accountService,
                          PhotoService photoService) {
        this.houseService = houseService;
        this.accountService = accountService;
        this.photoService = photoService;
    }

    @Override
    public ResponseEntity<List<HouseDto>> getAll(Glamping glamping,
                                                 HouseType houseType,
                                                 HouseStatus houseStatus,
                                                 Integer cost) {
        final HouseFilter houseFilter = new HouseFilter(
                glamping,
                houseType,
                houseStatus,
                cost
        );
        return ResponseEntity.ok(houseService.getAll(houseFilter));
    }

    @Override
    public ResponseEntity<HouseDto> get(Long id) {
        return ResponseEntity.ok(houseService.get(id));
    }

    @Override
    public ResponseEntity<Set<LocalDate>> getBookedDates(Long id) {
        return ResponseEntity.ok(houseService.getBookedDates(id));
    }

    @Override
    public ResponseEntity<String> getCode(Long id, String token) {
        final Account account = accountService.getAccountByToken(token);
        return ResponseEntity.ok(houseService.getCode(id, account));
    }

    @Override
    public ResponseEntity<HouseDto> add(HouseDto houseDto, String token) {
        final Account account = accountService.getAccountByToken(token);
        photoService.checkIfExists(houseDto.getPhotoName());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(houseService.save(houseDto, account));
    }

    @Override
    public ResponseEntity<HouseDto> edit(Long id, HouseDto newHouseDto, String token) {
        final Account account = accountService.getAccountByToken(token);
        photoService.checkIfExists(newHouseDto.getPhotoName());
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(houseService.edit(id, newHouseDto, account));
    }

    @Override
    public ResponseEntity<Void> delete(Long id, String token) {
        final Account account = accountService.getAccountByToken(token);
        houseService.delete(id, account);
        return ResponseEntity.noContent().build();
    }
}
