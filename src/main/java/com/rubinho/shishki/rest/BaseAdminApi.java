package com.rubinho.shishki.rest;

import com.rubinho.shishki.dto.GlampingResponseDto;
import com.rubinho.shishki.dto.PotentialOwnerDto;
import com.rubinho.shishki.dto.SecuredAccountDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface BaseAdminApi {
    @GetMapping("/admin/owners")
    ResponseEntity<List<PotentialOwnerDto>> getAllPotentialOwners();

    @GetMapping("/admin/accounts")
    ResponseEntity<List<SecuredAccountDto>> getAllAccounts();

    @GetMapping("/admin/glampings")
    ResponseEntity<List<GlampingResponseDto>> getAllGlampingsForReview();
}
