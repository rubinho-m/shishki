package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.dto.ReviewDto;
import com.rubinho.shishki.mappers.ReviewMapper;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.model.Review;
import com.rubinho.shishki.model.Role;
import com.rubinho.shishki.repository.ReviewRepository;
import com.rubinho.shishki.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }

    @Override
    public List<ReviewDto> getAll() {
        return reviewRepository.findAll()
                .stream()
                .map(reviewMapper::toDto)
                .toList();
    }

    @Override
    public ReviewDto get(Long id) {
        return reviewMapper.toDto(
                reviewRepository.findById(id)
                        .orElseThrow(
                                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No review by this id")
                        )
        );
    }

    @Override
    public ReviewDto save(ReviewDto reviewDto, Account account) {
        check(reviewDto.getLogin(), account);
        return reviewMapper.toDto(
                reviewRepository.save(
                        reviewMapper.toEntity(reviewDto)
                )
        );
    }

    @Override
    public ReviewDto edit(Long id, ReviewDto reviewDto, Account account) {
        reviewDto.setId(id);
        return save(reviewDto, account);
    }

    @Override
    public void delete(Long id, Account account) {
        final Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No review by this id"));
        check(review.getAccount().getLogin(), account);
        reviewRepository.delete(review);
    }

    private void check(String login, Account account) {
        if (account.getRole().equals(Role.ADMIN)){
            return;
        }
        if (!account.getLogin().equals(login)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not own this review");
        }
    }
}
