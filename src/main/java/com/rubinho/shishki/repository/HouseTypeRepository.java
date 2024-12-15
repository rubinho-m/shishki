package com.rubinho.shishki.repository;

import com.rubinho.shishki.model.HouseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HouseTypeRepository extends JpaRepository<HouseType, Long> {
    Optional<HouseType> findByType(String type);
}
