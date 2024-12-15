package com.rubinho.shishki.mappers;

import com.rubinho.shishki.dto.ReviewDto;
import com.rubinho.shishki.model.Review;

public interface ReviewMapper {
    Review toEntity(ReviewDto reviewDto);

    ReviewDto toDto(Review review);
}
