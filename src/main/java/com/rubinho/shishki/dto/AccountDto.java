package com.rubinho.shishki.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class AccountDto {
    private Long id;

    private String login;

    private String password;

    private GuestDto guest;
}
