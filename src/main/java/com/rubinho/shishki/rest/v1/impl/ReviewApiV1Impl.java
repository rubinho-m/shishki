package com.rubinho.shishki.rest.v1.impl;

import com.rubinho.shishki.dto.ReviewDto;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.rest.v1.ReviewApiV1;
import com.rubinho.shishki.services.AccountService;
import com.rubinho.shishki.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReviewApiV1Impl implements ReviewApiV1 {
    private final ReviewService reviewService;
    private final AccountService accountService;

    @Autowired
    public ReviewApiV1Impl(ReviewService reviewService, AccountService accountService) {
        this.reviewService = reviewService;
        this.accountService = accountService;
    }

    @Override
    public ResponseEntity<List<ReviewDto>> getAll() {
        return ResponseEntity.ok(reviewService.getAll());
    }

    @Override
    public ResponseEntity<ReviewDto> get(Long id) {
        return ResponseEntity.ok(reviewService.get(id));
    }

    @Override
    public ResponseEntity<ReviewDto> add(ReviewDto reviewDto, String token) {
        final Account account = accountService.getAccountByToken(token);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reviewService.save(reviewDto, account));
    }

    @Override
    public ResponseEntity<ReviewDto> edit(Long id, ReviewDto newReviewDto, String token) {
        final Account account = accountService.getAccountByToken(token);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(reviewService.edit(id, newReviewDto, account));
    }

    @Override
    public ResponseEntity<Void> delete(Long id, String token) {
        final Account account = accountService.getAccountByToken(token);
        reviewService.delete(id, account);
        return ResponseEntity.noContent().build();
    }
}
