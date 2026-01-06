package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.dto.ReviewDto;
import com.rubinho.shishki.exceptions.AccountNotFoundException;
import com.rubinho.shishki.exceptions.GlampingNotFoundException;
import com.rubinho.shishki.exceptions.rest.ForbiddenException;
import com.rubinho.shishki.mappers.ReviewMapper;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.model.Role;
import com.rubinho.shishki.repository.ReviewRepository;
import com.rubinho.shishki.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<ReviewDto> get(Long id) {
        return reviewRepository.findById(id).map(reviewMapper::toDto);
    }

    @Override
    public ReviewDto save(ReviewDto reviewDto, Account account) throws GlampingNotFoundException, AccountNotFoundException {
        check(reviewDto.getLogin(), account);
        return reviewMapper.toDto(
                reviewRepository.save(
                        reviewMapper.toEntity(reviewDto)
                )
        );
    }

    @Override
    public Optional<ReviewDto> edit(Long id, ReviewDto reviewDto, Account account) throws GlampingNotFoundException, AccountNotFoundException {
        if (!reviewRepository.existsById(id)) {
            return Optional.empty();
        }
        reviewDto.setId(id);
        return Optional.of(save(reviewDto, account));
    }

    @Override
    public void delete(Long id, Account account) {
        reviewRepository.findById(id).ifPresent(review ->  {
            check(review.getAccount().getLogin(), account);
            reviewRepository.delete(review);
        });
    }

    private void check(String login, Account account) {
        if (account.getRole().equals(Role.ADMIN)){
            return;
        }
        if (!account.getLogin().equals(login)){
            throw new ForbiddenException("You are not own this review");
        }
    }
}