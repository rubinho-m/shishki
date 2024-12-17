package com.rubinho.shishki.dto;

import com.rubinho.shishki.model.GlampingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class GlampingResponseDto {
    private Long id;

    private String ownerLogin;

    private String address;

    private String description;

    private String photoName;

    private GlampingStatus status;
}
