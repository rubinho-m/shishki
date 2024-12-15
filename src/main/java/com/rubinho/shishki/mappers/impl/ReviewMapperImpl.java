package com.rubinho.shishki.mappers.impl;

import com.rubinho.shishki.dto.ReviewDto;
import com.rubinho.shishki.mappers.ReviewMapper;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.model.Glamping;
import com.rubinho.shishki.model.Review;
import com.rubinho.shishki.repository.AccountRepository;
import com.rubinho.shishki.repository.GlampingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ReviewMapperImpl implements ReviewMapper {
    private final GlampingRepository glampingRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public ReviewMapperImpl(GlampingRepository glampingRepository,
                            AccountRepository accountRepository) {
        this.glampingRepository = glampingRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Review toEntity(ReviewDto reviewDto) {
        final Glamping glamping = glampingRepository.findById(reviewDto.getGlampingId())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Glamping not found by id: %d"
                                .formatted(reviewDto.getGlampingId()))
                );
        final Account account = accountRepository.findByLogin(reviewDto.getLogin())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found by login: %s"
                                .formatted(reviewDto.getLogin()))
                );

        return Review.builder()
                .id(reviewDto.getId())
                .glamping(glamping)
                .account(account)
                .content(reviewDto.getContent())
                .rating(reviewDto.getRating())
                .build();
    }

    @Override
    public ReviewDto toDto(Review review) {
        return ReviewDto.builder()
                .id(review.getId())
                .glampingId(review.getGlamping().getId())
                .login(review.getAccount().getLogin())
                .content(review.getContent())
                .rating(review.getRating())
                .build();
    }
}
