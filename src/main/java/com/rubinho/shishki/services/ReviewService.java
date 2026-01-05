package com.rubinho.shishki.services;

import com.rubinho.shishki.dto.ReviewDto;
import com.rubinho.shishki.exceptions.AccountNotFoundException;
import com.rubinho.shishki.exceptions.GlampingNotFoundException;
import com.rubinho.shishki.model.Account;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    List<ReviewDto> getAll();

    Optional<ReviewDto> get(Long id);

    ReviewDto save(ReviewDto reviewDto, Account account) throws GlampingNotFoundException, AccountNotFoundException;

    Optional<ReviewDto> edit(Long id, ReviewDto reviewDto, Account account) throws GlampingNotFoundException, AccountNotFoundException;

    void delete(Long id, Account account);
}
