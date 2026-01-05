package com.rubinho.shishki.rest.impl;

import com.rubinho.shishki.dto.HouseTypeDto;
import com.rubinho.shishki.exceptions.rest.BadRequestException;
import com.rubinho.shishki.exceptions.rest.NotFoundException;
import com.rubinho.shishki.rest.HouseTypeApi;
import com.rubinho.shishki.services.HouseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HouseTypeApiImpl implements HouseTypeApi {
    private final HouseTypeService houseTypeService;

    @Autowired
    public HouseTypeApiImpl(HouseTypeService houseTypeService) {
        this.houseTypeService = houseTypeService;
    }

    @Override
    public ResponseEntity<List<HouseTypeDto>> getAll() {
        return ResponseEntity.ok(houseTypeService.getAll());
    }

    @Override
    public ResponseEntity<HouseTypeDto> get(Long id) {
        return ResponseEntity.ok(
                houseTypeService.get(id)
                        .orElseThrow(() -> new NotFoundException("House type with id %d not found".formatted(id)))
        );
    }

    @Override
    public ResponseEntity<HouseTypeDto> add(HouseTypeDto houseTypeDto) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(houseTypeService.save(houseTypeDto));
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("House type: %s already exists".formatted(houseTypeDto.getType()));
        }
    }

    @Override
    public ResponseEntity<HouseTypeDto> edit(Long id, HouseTypeDto newHouseTypeDto) {
        try {
            final HouseTypeDto houseType = houseTypeService.edit(id, newHouseTypeDto)
                    .orElseThrow(() -> new NotFoundException("House type with id %d not found".formatted(id)));
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(houseType);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(
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
