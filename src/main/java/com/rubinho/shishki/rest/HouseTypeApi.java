package com.rubinho.shishki.rest;

import com.rubinho.shishki.config.NoAuth;
import com.rubinho.shishki.config.OwnerAuthorization;
import com.rubinho.shishki.dto.HouseTypeDto;
import com.rubinho.shishki.rest.versions.ApiVersioned;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@ApiVersioned(path = {"/api/v1", "/api/v2"})
public interface HouseTypeApi {
    @GetMapping("/types")
    @NoAuth
    ResponseEntity<List<HouseTypeDto>> getAll();

    @GetMapping("/types/{id}")
    @NoAuth
    ResponseEntity<HouseTypeDto> get(@PathVariable Long id);

    @PostMapping("/types")
    @OwnerAuthorization
    ResponseEntity<HouseTypeDto> add(@RequestBody HouseTypeDto houseTypeDto);

    @PutMapping("/types/{id}")
    @OwnerAuthorization
    ResponseEntity<HouseTypeDto> edit(@PathVariable Long id,
                                      @RequestBody HouseTypeDto newHouseTypeDto);

    @DeleteMapping("/types/{id}")
    @OwnerAuthorization
    ResponseEntity<Void> delete(@PathVariable Long id);
}
