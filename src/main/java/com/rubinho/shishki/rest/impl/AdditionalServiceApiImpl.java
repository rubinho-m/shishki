package com.rubinho.shishki.rest.impl;

import com.rubinho.shishki.dto.AdditionalServiceDto;
import com.rubinho.shishki.rest.AdditionalServiceApi;
import com.rubinho.shishki.services.AdditionalServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class AdditionalServiceApiImpl implements AdditionalServiceApi {
    private final AdditionalServiceService additionalServiceService;

    @Autowired
    public AdditionalServiceApiImpl(AdditionalServiceService additionalServiceService) {
        this.additionalServiceService = additionalServiceService;
    }

    @Override
    public ResponseEntity<List<AdditionalServiceDto>> getAll() {
        return ResponseEntity.ok(additionalServiceService.getAll());
    }

    @Override
    public ResponseEntity<AdditionalServiceDto> get(Long id) {
        return ResponseEntity.ok(additionalServiceService.get(id));
    }

    @Override
    public ResponseEntity<AdditionalServiceDto> add(AdditionalServiceDto additionalServiceDto) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(additionalServiceService.save(additionalServiceDto));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Service with name: %s already exists".formatted(additionalServiceDto.getName())
            );
        }
    }

    @Override
    public ResponseEntity<AdditionalServiceDto> edit(Long id, AdditionalServiceDto newAdditionalServiceDto) {
        try {
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(additionalServiceService.edit(id, newAdditionalServiceDto));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Service with name: %s already exists".formatted(newAdditionalServiceDto.getName())
            );
        }
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        additionalServiceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
