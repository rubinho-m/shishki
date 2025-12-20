package com.rubinho.shishki.rest.v1.impl;

import com.rubinho.shishki.dto.HouseStatusDto;
import com.rubinho.shishki.rest.v1.HouseStatusApiV1;
import com.rubinho.shishki.services.HouseStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HouseStatusApiV1Impl implements HouseStatusApiV1 {
    private final HouseStatusService houseStatusService;

    @Autowired
    public HouseStatusApiV1Impl(HouseStatusService houseStatusService) {
        this.houseStatusService = houseStatusService;
    }

    @Override
    public ResponseEntity<List<HouseStatusDto>> getAll() {
        return ResponseEntity.ok(houseStatusService.getAll());
    }

    @Override
    public ResponseEntity<HouseStatusDto> get(Long id) {
        return ResponseEntity.ok(houseStatusService.get(id));
    }
}
