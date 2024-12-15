package com.rubinho.shishki.repository;

import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByRole(Role role);

    Optional<Account> findByLogin(String login);
}
