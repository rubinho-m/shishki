package com.rubinho.shishki.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ReviewDto {
    private Long id;

    private String login;

    private Long glampingId;

    private String content;

    private Integer rating;
}
