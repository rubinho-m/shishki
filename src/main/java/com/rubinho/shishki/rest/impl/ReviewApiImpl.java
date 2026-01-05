package com.rubinho.shishki.rest.impl;

import com.rubinho.shishki.dto.ReviewDto;
import com.rubinho.shishki.exceptions.NotFoundException;
import com.rubinho.shishki.exceptions.UnauthorizedException;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.rest.ReviewApi;
import com.rubinho.shishki.services.AccountService;
import com.rubinho.shishki.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReviewApiImpl implements ReviewApi {
    private final ReviewService reviewService;
    private final AccountService accountService;

    @Autowired
    public ReviewApiImpl(ReviewService reviewService, AccountService accountService) {
        this.reviewService = reviewService;
        this.accountService = accountService;
    }

    @Override
    public ResponseEntity<List<ReviewDto>> getAll() {
        return ResponseEntity.ok(reviewService.getAll());
    }

    @Override
    public ResponseEntity<ReviewDto> get(Long id) {
        return ResponseEntity.ok(
                reviewService.get(id)
                        .orElseThrow(() -> new NotFoundException("Review with id %d not found".formatted(id)))
        );
    }

    @Override
    public ResponseEntity<ReviewDto> add(ReviewDto reviewDto, String token) {
        final Account account = getAccount(token);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reviewService.save(reviewDto, account));
    }

    @Override
    public ResponseEntity<ReviewDto> edit(Long id, ReviewDto newReviewDto, String token) {
        final Account account = getAccount(token);
        final ReviewDto reviewDto = reviewService.edit(id, newReviewDto, account)
                .orElseThrow(() -> new NotFoundException("Review with id %d not found".formatted(id)));
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(reviewDto);
    }

    @Override
    public ResponseEntity<Void> delete(Long id, String token) {
        final Account account = getAccount(token);
        reviewService.delete(id, account);
        return ResponseEntity.noContent().build();
    }

    private Account getAccount(String token) {
        return accountService.getAccountByToken(token)
                .orElseThrow(() -> new UnauthorizedException("Not found user by auth token"));
    }
}
