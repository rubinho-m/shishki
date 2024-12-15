package com.rubinho.shishki.repository;

import com.rubinho.shishki.model.PhotoOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhotoOwnerRepository extends JpaRepository<PhotoOwner, Long> {
    Optional<PhotoOwner> getPhotoOwnerByFileName(String fileName);
}
