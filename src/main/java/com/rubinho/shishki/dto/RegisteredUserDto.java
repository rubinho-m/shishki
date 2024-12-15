package com.rubinho.shishki.dto;

import com.rubinho.shishki.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class RegisteredUserDto {
    private Long id;
    private String login;
    private String token;
    private Role role;
}