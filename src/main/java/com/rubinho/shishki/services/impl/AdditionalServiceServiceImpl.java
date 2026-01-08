package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.dto.AdditionalServiceDto;
import com.rubinho.shishki.mappers.AdditionalServiceMapper;
import com.rubinho.shishki.repository.AdditionalServiceRepository;
import com.rubinho.shishki.services.AdditionalServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<AdditionalServiceDto> get(Long id) {
        return additionalServiceRepository.findById(id).map(additionalServiceMapper::toDto);
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
    public Optional<AdditionalServiceDto> edit(Long id, AdditionalServiceDto additionalServiceDto) {
        if (!additionalServiceRepository.existsById(id)) {
            return Optional.empty();
        }
        additionalServiceDto.setId(id);
        return Optional.of(save(additionalServiceDto));
    }

    @Override
    public void delete(Long id) {
        additionalServiceRepository.findById(id).ifPresent(additionalServiceRepository::delete);
    }
}