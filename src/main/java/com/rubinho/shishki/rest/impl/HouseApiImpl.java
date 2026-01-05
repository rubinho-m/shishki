package com.rubinho.shishki.rest.impl;

import com.rubinho.shishki.dto.HouseDto;
import com.rubinho.shishki.enums.StorageType;
import com.rubinho.shishki.exceptions.BadRequestException;
import com.rubinho.shishki.exceptions.NotFoundException;
import com.rubinho.shishki.exceptions.UnauthorizedException;
import com.rubinho.shishki.filters.HouseFilter;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.model.Glamping;
import com.rubinho.shishki.model.HouseStatus;
import com.rubinho.shishki.model.HouseType;
import com.rubinho.shishki.rest.HouseApi;
import com.rubinho.shishki.rest.versions.ApiVersioningUtils;
import com.rubinho.shishki.services.AccountService;
import com.rubinho.shishki.services.HouseService;
import com.rubinho.shishki.services.PhotoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
public class HouseApiImpl implements HouseApi {
    private final HouseService houseService;
    private final AccountService accountService;
    private final PhotoService photoService;
    private final ApiVersioningUtils apiVersioningUtils;

    @Autowired
    public HouseApiImpl(HouseService houseService,
                        AccountService accountService,
                        PhotoService photoService,
                        ApiVersioningUtils apiVersioningUtils) {
        this.houseService = houseService;
        this.accountService = accountService;
        this.photoService = photoService;
        this.apiVersioningUtils = apiVersioningUtils;
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
        return ResponseEntity.ok(
                houseService.get(id)
                        .orElseThrow(() -> new NotFoundException("House with id %d not found".formatted(id)))
        );
    }

    @Override
    public ResponseEntity<Set<LocalDate>> getBookedDates(Long id) {
        return ResponseEntity.ok(
                houseService.getBookedDates(id)
                        .orElseThrow(() -> new NotFoundException("House with id %d not found".formatted(id)))
        );
    }

    @Override
    public ResponseEntity<String> getCode(Long id, String token) {
        final Account account = getAccount(token);
        return ResponseEntity.ok(
                houseService.getCode(id, account)
                        .orElseThrow(() -> new NotFoundException("House with id %d not found".formatted(id)))
        );
    }

    @Override
    public ResponseEntity<HouseDto> add(HttpServletRequest httpServletRequest,
                                        HouseDto houseDto,
                                        String token) {
        final Account account = getAccount(token);
        checkPhotoExists(
                houseDto.getPhotoName(),
                apiVersioningUtils.storageType(httpServletRequest.getRequestURI())
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(houseService.save(houseDto, account));
    }

    @Override
    public ResponseEntity<HouseDto> edit(HttpServletRequest httpServletRequest,
                                         Long id,
                                         HouseDto newHouseDto,
                                         String token) {
        final Account account = getAccount(token);
        checkPhotoExists(
                newHouseDto.getPhotoName(),
                apiVersioningUtils.storageType(httpServletRequest.getRequestURI())
        );
        final HouseDto house = houseService.edit(id, newHouseDto, account)
                .orElseThrow(() -> new NotFoundException("House with id %d not found".formatted(id)));
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(house);
    }

    @Override
    public ResponseEntity<Void> delete(Long id, String token) {
        final Account account = getAccount(token);
        houseService.delete(id, account);
        return ResponseEntity.noContent().build();
    }

    private void checkPhotoExists(String photoName, StorageType storageType) {
        try {
            photoService.checkIfExists(storageType, photoName);
        } catch (FileNotFoundException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    private Account getAccount(String token) {
        return accountService.getAccountByToken(token)
                .orElseThrow(() -> new UnauthorizedException("Not found user by auth token"));
    }
}
