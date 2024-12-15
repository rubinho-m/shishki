package com.rubinho.shishki.repository;

import com.rubinho.shishki.model.HouseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HouseStatusRepository extends JpaRepository<HouseStatus, Long> {
    Optional<HouseStatus> findByStatus(String status);
}
