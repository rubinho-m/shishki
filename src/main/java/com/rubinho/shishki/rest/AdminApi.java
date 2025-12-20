package com.rubinho.shishki.rest;

import com.rubinho.shishki.dto.GlampingResponseDto;
import com.rubinho.shishki.dto.PotentialOwnerDto;
import com.rubinho.shishki.dto.SecuredAccountDto;
import com.rubinho.shishki.rest.versions.ApiVersioned;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@ApiVersioned(path = {"/api/v2"})
public interface AdminApi {
    @GetMapping("/admin/owners")
    ResponseEntity<List<PotentialOwnerDto>> getAllPotentialOwners();

    @GetMapping("/admin/accounts")
    ResponseEntity<List<SecuredAccountDto>> getAllAccounts();

    @GetMapping("/admin/glampings")
    ResponseEntity<List<GlampingResponseDto>> getAllGlampingsForReview();

    @PutMapping("/admin/glampings/{id}/{ok}")
    ResponseEntity<Void> checkGlamping(@PathVariable Long id,
                                       @PathVariable boolean ok);

    @PutMapping("/admin/users/{id}/{role}")
    ResponseEntity<Void> addRole(@PathVariable Long id,
                                 @PathVariable String role);
}
