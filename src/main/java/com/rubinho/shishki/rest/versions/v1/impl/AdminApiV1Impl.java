package com.rubinho.shishki.rest.versions.v1.impl;

import com.rubinho.shishki.dto.GlampingResponseDto;
import com.rubinho.shishki.dto.PotentialOwnerDto;
import com.rubinho.shishki.dto.SecuredAccountDto;
import com.rubinho.shishki.model.GlampingStatus;
import com.rubinho.shishki.model.Role;
import com.rubinho.shishki.rest.versions.v1.AdminApiV1;
import com.rubinho.shishki.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminApiV1Impl implements AdminApiV1 {
    private final AdminService adminService;

    @Autowired
    public AdminApiV1Impl(AdminService adminService) {
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
    public ResponseEntity<Void> approveGlamping(Long id) {
        adminService.setNewGlampingStatus(id, GlampingStatus.APPROVED);
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<Void> rejectGlamping(Long id) {
        adminService.setNewGlampingStatus(id, GlampingStatus.REJECTED);
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<Void> addOwner(Long id) {
        adminService.setNewRole(id, Role.OWNER);
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<Void> addUser(Long id) {
        adminService.setNewRole(id, Role.USER);
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<Void> addStaff(Long id) {
        adminService.setNewRole(id, Role.STAFF);
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<Void> addAdmin(Long id) {
        adminService.setNewRole(id, Role.ADMIN);
        return ResponseEntity.accepted().build();
    }
}
