package com.rubinho.shishki.rest.v1.impl;

import com.rubinho.shishki.dto.HouseTypeDto;
import com.rubinho.shishki.rest.v1.HouseTypeApiV1;
import com.rubinho.shishki.services.HouseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class HouseTypeApiV1Impl implements HouseTypeApiV1 {
    private final HouseTypeService houseTypeService;

    @Autowired
    public HouseTypeApiV1Impl(HouseTypeService houseTypeService) {
        this.houseTypeService = houseTypeService;
    }

    @Override
    public ResponseEntity<List<HouseTypeDto>> getAll() {
        return ResponseEntity.ok(houseTypeService.getAll());
    }

    @Override
    public ResponseEntity<HouseTypeDto> get(Long id) {
        return ResponseEntity.ok(houseTypeService.get(id));
    }

    @Override
    public ResponseEntity<HouseTypeDto> add(HouseTypeDto houseTypeDto) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(houseTypeService.save(houseTypeDto));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "House type: %s already exists".formatted(houseTypeDto.getType())
            );
        }
    }

    @Override
    public ResponseEntity<HouseTypeDto> edit(Long id, HouseTypeDto newHouseTypeDto) {
        try {
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(houseTypeService.edit(id, newHouseTypeDto));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "House type: %s already exists".formatted(newHouseTypeDto.getType())
            );
        }
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        houseTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
