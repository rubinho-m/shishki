package com.rubinho.shishki.repository;

import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    Set<Guest> findAllByIdIn(List<Long> ids);

    List<Guest> findAllByAccount(Account account);
}
