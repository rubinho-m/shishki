package com.rubinho.shishki.rest.impl;

import com.rubinho.shishki.dto.PotentialOwnerDto;
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
