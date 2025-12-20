package com.rubinho.shishki.rest.v1;

import com.rubinho.shishki.rest.BaseAdminApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1")
public interface AdminApiV1 extends BaseAdminApi {
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
