package com.rubinho.shishki.mappers;

import com.rubinho.shishki.dto.ReviewDto;
import com.rubinho.shishki.exceptions.AccountNotFoundException;
import com.rubinho.shishki.exceptions.GlampingNotFoundException;
import com.rubinho.shishki.model.Review;

public interface ReviewMapper {
    Review toEntity(ReviewDto reviewDto) throws AccountNotFoundException, GlampingNotFoundException;

    ReviewDto toDto(Review review);
}
