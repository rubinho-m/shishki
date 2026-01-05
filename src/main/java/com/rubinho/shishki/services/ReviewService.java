package com.rubinho.shishki.services;

import com.rubinho.shishki.dto.ReviewDto;
import com.rubinho.shishki.model.Account;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    List<ReviewDto> getAll();

    Optional<ReviewDto> get(Long id);

    ReviewDto save(ReviewDto reviewDto, Account account);

    Optional<ReviewDto> edit(Long id, ReviewDto reviewDto, Account account);

    void delete(Long id, Account account);
}
