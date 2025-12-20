package com.rubinho.shishki.rest.versions.v1;

import com.rubinho.shishki.dto.GlampingResponseDto;
import com.rubinho.shishki.dto.PotentialOwnerDto;
import com.rubinho.shishki.dto.SecuredAccountDto;
import com.rubinho.shishki.rest.versions.ApiVersioned;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Deprecated(since = "v2")
@ApiVersioned(path = {"/api/v1"})
public interface AdminApiV1 {
    @GetMapping("/admin/owners")
    ResponseEntity<List<PotentialOwnerDto>> getAllPotentialOwners();

    @GetMapping("/admin/accounts")
    ResponseEntity<List<SecuredAccountDto>> getAllAccounts();

    @GetMapping("/admin/glampings")
    ResponseEntity<List<GlampingResponseDto>> getAllGlampingsForReview();

    @PutMapping("/admin/glampings/{id}:approve")
    ResponseEntity<Void> approveGlamping(@PathVariable Long id);

    @PutMapping("/admin/glampings/{id}:reject")
    ResponseEntity<Void> rejectGlamping(@PathVariable Long id);

    @PutMapping("/admin/{id}:owner")
    ResponseEntity<Void> addOwner(@PathVariable Long id);

    @PutMapping("/admin/{id}:user")
    ResponseEntity<Void> addUser(@PathVariable Long id);

    @PutMapping("/admin/{id}:staff")
    ResponseEntity<Void> addStaff(@PathVariable Long id);

    @PutMapping("/admin/{id}:admin")
    ResponseEntity<Void> addAdmin(@PathVariable Long id);
}
