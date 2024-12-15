package com.rubinho.shishki.repository;

import com.rubinho.shishki.model.AdditionalService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AdditionalServiceRepository extends JpaRepository<AdditionalService, Long> {
    Set<AdditionalService> findAllByIdIn(List<Long> ids);
}
