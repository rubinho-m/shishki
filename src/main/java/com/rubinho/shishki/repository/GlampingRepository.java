package com.rubinho.shishki.repository;

import com.rubinho.shishki.model.Glamping;
import com.rubinho.shishki.model.GlampingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GlampingRepository extends JpaRepository<Glamping, Long> {
    List<Glamping> findAllByGlampingStatus(GlampingStatus glampingStatus);
}
