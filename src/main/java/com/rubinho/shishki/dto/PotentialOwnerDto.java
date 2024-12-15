package com.rubinho.shishki.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class PotentialOwnerDto {
    private Long id;

    private String login;
}
