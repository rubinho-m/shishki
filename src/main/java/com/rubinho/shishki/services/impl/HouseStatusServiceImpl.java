package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.dto.HouseStatusDto;
import com.rubinho.shishki.mappers.HouseStatusMapper;
import com.rubinho.shishki.repository.HouseStatusRepository;
import com.rubinho.shishki.services.HouseStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class HouseStatusServiceImpl implements HouseStatusService {
    private final HouseStatusRepository houseStatusRepository;
    private final HouseStatusMapper houseStatusMapper;

    @Autowired
    public HouseStatusServiceImpl(HouseStatusRepository houseStatusRepository,
                                  HouseStatusMapper houseStatusMapper) {
        this.houseStatusRepository = houseStatusRepository;
        this.houseStatusMapper = houseStatusMapper;
    }

    @Override
    public List<HouseStatusDto> getAll() {
        return houseStatusRepository.findAll()
                .stream()
                .map(houseStatusMapper::toDto)
                .toList();
    }

    @Override
    public HouseStatusDto get(Long id) {
        return houseStatusMapper.toDto(
                houseStatusRepository.findById(id)
                        .orElseThrow(
                                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No type by this id")
                        )
        );
    }
}
