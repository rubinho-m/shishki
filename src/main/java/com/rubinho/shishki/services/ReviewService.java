package com.rubinho.shishki.services;

import com.rubinho.shishki.dto.ReviewDto;
import com.rubinho.shishki.model.Account;

import java.util.List;

public interface ReviewService {
    List<ReviewDto> getAll();

    ReviewDto get(Long id);

    ReviewDto save(ReviewDto reviewDto, Account account);

    ReviewDto edit(Long id, ReviewDto reviewDto, Account account);

    void delete(Long id, Account account);
}
