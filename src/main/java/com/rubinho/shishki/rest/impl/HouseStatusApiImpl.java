package com.rubinho.shishki.rest.impl;

import com.rubinho.shishki.dto.HouseStatusDto;
import com.rubinho.shishki.exceptions.rest.NotFoundException;
import com.rubinho.shishki.rest.HouseStatusApi;
import com.rubinho.shishki.services.HouseStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HouseStatusApiImpl implements HouseStatusApi {
    private final HouseStatusService houseStatusService;

    @Autowired
    public HouseStatusApiImpl(HouseStatusService houseStatusService) {
        this.houseStatusService = houseStatusService;
    }

    @Override
    public ResponseEntity<List<HouseStatusDto>> getAll() {
        return ResponseEntity.ok(houseStatusService.getAll());
    }

    @Override
    public ResponseEntity<HouseStatusDto> get(Long id) {
        return ResponseEntity.ok(
                houseStatusService.get(id).orElseThrow(
                        () -> new NotFoundException("House status with id %d not found".formatted(id))
                )
        );
    }
}
