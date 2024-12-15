package com.rubinho.shishki.rest;

import com.rubinho.shishki.dto.PotentialOwnerDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

public interface AdminApi {
    @GetMapping("/admin")
    ResponseEntity<List<PotentialOwnerDto>> getAllPotentialOwners();

    @PutMapping("/admin/{id}:owner")
    ResponseEntity<Void> addOwner(@PathVariable("id") Long id);

    @PutMapping("/admin/{id}:user")
    ResponseEntity<Void> addUser(@PathVariable("id") Long id);

    @PutMapping("/admin/{id}:staff")
    ResponseEntity<Void> addStaff(@PathVariable("id") Long id);

    @PutMapping("/admin/{id}:admin")
    ResponseEntity<Void> addAdmin(@PathVariable("id") Long id);
}
