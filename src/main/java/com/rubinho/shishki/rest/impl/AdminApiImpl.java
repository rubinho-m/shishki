package com.rubinho.shishki.rest.impl;

import com.rubinho.shishki.dto.GlampingResponseDto;
import com.rubinho.shishki.dto.PotentialOwnerDto;
import com.rubinho.shishki.dto.SecuredAccountDto;
import com.rubinho.shishki.exceptions.BadRequestException;
import com.rubinho.shishki.exceptions.NotFoundException;
import com.rubinho.shishki.model.GlampingStatus;
import com.rubinho.shishki.model.Role;
import com.rubinho.shishki.rest.AdminApi;
import com.rubinho.shishki.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminApiImpl implements AdminApi {
    private final AdminService adminService;

    @Autowired
    public AdminApiImpl(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public ResponseEntity<List<PotentialOwnerDto>> getAllPotentialOwners() {
        return ResponseEntity.ok(adminService.getAllPotentialOwners());
    }

    @Override
    public ResponseEntity<List<SecuredAccountDto>> getAllAccounts() {
        return ResponseEntity.ok(adminService.getAllAccounts());
    }

    @Override
    public ResponseEntity<List<GlampingResponseDto>> getAllGlampingsForReview() {
        return ResponseEntity.ok(adminService.getAllGlampingsForReview());
    }

    @Override
    public ResponseEntity<Void> checkGlamping(Long id, boolean ok) {
        final GlampingStatus glampingStatus = ok ? GlampingStatus.APPROVED : GlampingStatus.REJECTED;
        if (adminService.setNewGlampingStatus(id, glampingStatus).isEmpty()) {
            throw new NotFoundException("Glamping with id %d not found".formatted(id));
        }
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<Void> addRole(Long id, String role) {
        if (adminService.setNewRole(id, mapRole(role)).isEmpty()) {
            throw new NotFoundException("Account with id %d not found".formatted(id));
        }
        return ResponseEntity.accepted().build();
    }

    private Role mapRole(String role) {
        try {
            return Role.valueOf(role.toUpperCase());
        } catch (Exception e) {
            throw new BadRequestException("Role %s doesn't exist");
        }
    }
}
