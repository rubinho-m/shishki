package com.rubinho.shishki.rest;

import com.rubinho.shishki.config.AdminAuthorization;
import com.rubinho.shishki.config.NoAuth;
import com.rubinho.shishki.dto.AdditionalServiceDto;
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
public interface AdditionalServiceApi {
    @GetMapping("/services")
    @NoAuth
    ResponseEntity<List<AdditionalServiceDto>> getAll();

    @GetMapping("/services/{id}")
    @NoAuth
    ResponseEntity<AdditionalServiceDto> get(@PathVariable Long id);

    @PostMapping("/services")
    @AdminAuthorization
    ResponseEntity<AdditionalServiceDto> add(@RequestBody AdditionalServiceDto additionalServiceDto);

    @PutMapping("/services/{id}")
    @AdminAuthorization
    ResponseEntity<AdditionalServiceDto> edit(@PathVariable Long id,
                                              @RequestBody AdditionalServiceDto newAdditionalServiceDto);

    @DeleteMapping("/services/{id}")
    @AdminAuthorization
    ResponseEntity<Void> delete(@PathVariable Long id);
}
