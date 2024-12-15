package com.rubinho.shishki.repository;

import com.rubinho.shishki.model.Glamping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlampingRepository extends JpaRepository<Glamping, Long> {
}
