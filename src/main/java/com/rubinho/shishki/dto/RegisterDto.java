package com.rubinho.shishki.dto;

import com.rubinho.shishki.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    private Long id;

    private String login;

    private String password;

    private GuestDto guest;

    private Role role;
}
