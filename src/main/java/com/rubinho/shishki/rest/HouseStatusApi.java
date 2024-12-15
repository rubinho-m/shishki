package com.rubinho.shishki.rest;

import com.rubinho.shishki.dto.HouseStatusDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface HouseStatusApi {
    @GetMapping("/statuses")
    ResponseEntity<List<HouseStatusDto>> getAll();

    @GetMapping("/statuses/{id}")
    ResponseEntity<HouseStatusDto> get(@PathVariable("id") Long id);
}
