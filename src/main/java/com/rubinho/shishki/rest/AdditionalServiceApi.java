package com.rubinho.shishki.rest;

import com.rubinho.shishki.dto.AdditionalServiceDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AdditionalServiceApi {
    @GetMapping("/services")
    ResponseEntity<List<AdditionalServiceDto>> getAll();

    @GetMapping("/services/{id}")
    ResponseEntity<AdditionalServiceDto> get(@PathVariable("id") Long id);

    @PostMapping("/services")
    ResponseEntity<AdditionalServiceDto> add(@RequestBody AdditionalServiceDto additionalServiceDto);

    @PutMapping("/services/{id}")
    ResponseEntity<AdditionalServiceDto> edit(@PathVariable("id") Long id,
                                              @RequestBody AdditionalServiceDto newAdditionalServiceDto);

    @DeleteMapping("/services/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") Long id);
}
