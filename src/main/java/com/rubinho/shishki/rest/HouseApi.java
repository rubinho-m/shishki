package com.rubinho.shishki.rest;

import com.rubinho.shishki.dto.HouseDto;
import com.rubinho.shishki.model.Glamping;
import com.rubinho.shishki.model.HouseStatus;
import com.rubinho.shishki.model.HouseType;
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

public interface HouseApi {
    @GetMapping("/houses")
    ResponseEntity<List<HouseDto>> getAll(@RequestParam(value = "glamping", required = false) Glamping glamping,
                                          @RequestParam(value = "type", required = false) HouseType houseType,
                                          @RequestParam(value = "status", required = false) HouseStatus houseStatus,
                                          @RequestParam(value = "cost", required = false) Integer cost);

    @GetMapping("/houses/{id}")
    ResponseEntity<HouseDto> get(@PathVariable("id") Long id);

    @GetMapping("/houses/{id}/booked")
    ResponseEntity<Set<LocalDate>> getBookedDates(@PathVariable("id") Long id);

    @GetMapping("/houses/{id}/code")
    ResponseEntity<String> getCode(@PathVariable("id") Long id, @RequestHeader("Authorization") String token);

    @PostMapping("/houses")
    ResponseEntity<HouseDto> add(@RequestBody HouseDto houseDto,
                                 @RequestHeader("Authorization") String token);

    @PutMapping("/houses/{id}")
    ResponseEntity<HouseDto> edit(@PathVariable("id") Long id,
                                  @RequestBody HouseDto newHouseDto,
                                  @RequestHeader("Authorization") String token);

    @DeleteMapping("/houses/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") Long id, @RequestHeader("Authorization") String token);
}
