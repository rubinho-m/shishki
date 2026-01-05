package com.rubinho.shishki.services;

import com.rubinho.shishki.dto.GlampingResponseDto;
import com.rubinho.shishki.dto.PotentialOwnerDto;
import com.rubinho.shishki.dto.SecuredAccountDto;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.model.Glamping;
import com.rubinho.shishki.model.GlampingStatus;
import com.rubinho.shishki.model.Role;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    List<PotentialOwnerDto> getAllPotentialOwners();

    List<SecuredAccountDto> getAllAccounts();

    List<GlampingResponseDto> getAllGlampingsForReview();

    /**
     * @return аккаунт с новой ролью
     */
    Optional<Account> setNewRole(Long id, Role role);

    /**
     * @return глэмпинг с новым статусом
     */
    Optional<Glamping> setNewGlampingStatus(Long id, GlampingStatus glampingStatus);
}
