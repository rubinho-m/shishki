package com.rubinho.shishki.repository;

import com.rubinho.shishki.model.ShopItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ShopItemRepository extends JpaRepository<ShopItem, Long> {
    Set<ShopItem> findAllByIdIn(List<Long> ids);
}