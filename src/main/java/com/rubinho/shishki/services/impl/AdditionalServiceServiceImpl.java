package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.dto.AdditionalServiceDto;
import com.rubinho.shishki.mappers.AdditionalServiceMapper;
import com.rubinho.shishki.model.AdditionalService;
import com.rubinho.shishki.repository.AdditionalServiceRepository;
import com.rubinho.shishki.services.AdditionalServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AdditionalServiceServiceImpl implements AdditionalServiceService {
    private final AdditionalServiceRepository additionalServiceRepository;
    private final AdditionalServiceMapper additionalServiceMapper;

    @Autowired
    public AdditionalServiceServiceImpl(AdditionalServiceRepository additionalServiceRepository,
                                        AdditionalServiceMapper additionalServiceMapper) {
        this.additionalServiceMapper = additionalServiceMapper;
        this.additionalServiceRepository = additionalServiceRepository;
    }

    @Override
    public List<AdditionalServiceDto> getAll() {
        return additionalServiceRepository.findAll()
                .stream()
                .map(additionalServiceMapper::toDto)
                .toList();
    }

    @Override
    public AdditionalServiceDto get(Long id) {
        return additionalServiceMapper.toDto(
                additionalServiceRepository.findById(id)
                        .orElseThrow(
                                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No service by this id")
                        )
        );
    }

    @Override
    public AdditionalServiceDto save(AdditionalServiceDto additionalServiceDto) {
        return additionalServiceMapper.toDto(
                additionalServiceRepository.save(
                        additionalServiceMapper.toEntity(additionalServiceDto)
                )
        );
    }

    @Override
    public AdditionalServiceDto edit(Long id, AdditionalServiceDto additionalServiceDto) {
        additionalServiceDto.setId(id);
        return save(additionalServiceDto);
    }

    @Override
    public void delete(Long id) {
        final AdditionalService additionalService = additionalServiceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No service by this id"));
        additionalServiceRepository.delete(additionalService);
    }
}
