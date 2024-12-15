package com.rubinho.shishki.services;

import com.rubinho.shishki.dto.PotentialOwnerDto;
import com.rubinho.shishki.model.Role;

import java.util.List;

public interface AdminService {
    List<PotentialOwnerDto> getAllPotentialOwners();

    void setNewRole(Long id, Role role);
}
