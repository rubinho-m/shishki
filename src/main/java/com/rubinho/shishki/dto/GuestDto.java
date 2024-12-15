package com.rubinho.shishki.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class GuestDto {
    private Long id;

    private String name;

    private String surname;

    private String phone;

    private String email;
}
