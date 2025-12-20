package com.rubinho.shishki.rest;

import com.rubinho.shishki.dto.HouseTypeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface BaseHouseTypeApi {
    @GetMapping("/types")
    ResponseEntity<List<HouseTypeDto>> getAll();

    @GetMapping("/types/{id}")
    ResponseEntity<HouseTypeDto> get(@PathVariable Long id);

    @PostMapping("/types")
    ResponseEntity<HouseTypeDto> add(@RequestBody HouseTypeDto houseTypeDto);

    @PutMapping("/types/{id}")
    ResponseEntity<HouseTypeDto> edit(@PathVariable Long id,
                                      @RequestBody HouseTypeDto newHouseTypeDto);

    @DeleteMapping("/types/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}
