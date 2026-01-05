package com.rubinho.shishki.rest;

import com.rubinho.shishki.config.NoAuth;
import com.rubinho.shishki.config.OwnerAuthorization;
import com.rubinho.shishki.dto.HouseDto;
import com.rubinho.shishki.model.Glamping;
import com.rubinho.shishki.model.HouseStatus;
import com.rubinho.shishki.model.HouseType;
import com.rubinho.shishki.rest.versions.ApiVersioned;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@ApiVersioned(path = {"/api/v1", "/api/v2"})
public interface HouseApi {
    @GetMapping("/houses")
    @NoAuth
    ResponseEntity<List<HouseDto>> getAll(@RequestParam(value = "glamping", required = false) Glamping glamping,
                                          @RequestParam(value = "type", required = false) HouseType houseType,
                                          @RequestParam(value = "status", required = false) HouseStatus houseStatus,
                                          @RequestParam(value = "cost", required = false) Integer cost);

    @GetMapping("/houses/{id}")
    @NoAuth
    ResponseEntity<HouseDto> get(@PathVariable Long id);

    @GetMapping("/houses/{id}/booked")
    @NoAuth
    ResponseEntity<Set<LocalDate>> getBookedDates(@PathVariable Long id);

    @GetMapping("/houses/{id}/code")
    @NoAuth
    ResponseEntity<String> getCode(@PathVariable Long id, @RequestHeader("Authorization") String token);

    @PostMapping("/houses")
    @OwnerAuthorization
    ResponseEntity<HouseDto> add(HttpServletRequest httpServletRequest,
                                 @RequestBody HouseDto houseDto,
                                 @RequestHeader("Authorization") String token);

    @PutMapping("/houses/{id}")
    @OwnerAuthorization
    ResponseEntity<HouseDto> edit(HttpServletRequest httpServletRequest,
                                  @PathVariable Long id,
                                  @RequestBody HouseDto newHouseDto,
                                  @RequestHeader("Authorization") String token);

    @DeleteMapping("/houses/{id}")
    @OwnerAuthorization
    ResponseEntity<Void> delete(@PathVariable Long id, @RequestHeader("Authorization") String token);
}
