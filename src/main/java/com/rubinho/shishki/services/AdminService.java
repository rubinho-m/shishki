package com.rubinho.shishki.services;

import com.rubinho.shishki.dto.GlampingResponseDto;
import com.rubinho.shishki.dto.PotentialOwnerDto;
import com.rubinho.shishki.dto.SecuredAccountDto;
import com.rubinho.shishki.model.GlampingStatus;
import com.rubinho.shishki.model.Role;

import java.util.List;

public interface AdminService {
    List<PotentialOwnerDto> getAllPotentialOwners();

    List<SecuredAccountDto> getAllAccounts();

    List<GlampingResponseDto> getAllGlampingsForReview();

    void setNewRole(Long id, Role role);

    void setNewGlampingStatus(Long id, GlampingStatus glampingStatus);
}
