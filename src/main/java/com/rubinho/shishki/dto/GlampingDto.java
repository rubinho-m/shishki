package com.rubinho.shishki.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class GlampingDto {
    private Long id;

    private String ownerLogin;

    private String address;

    private String description;

    private String photoName;
}
