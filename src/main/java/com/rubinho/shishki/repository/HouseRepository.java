package com.rubinho.shishki.repository;

import com.rubinho.shishki.model.House;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseRepository extends JpaRepository<House, Long> {
    List<House> findAll(Specification<House> spec);
}
