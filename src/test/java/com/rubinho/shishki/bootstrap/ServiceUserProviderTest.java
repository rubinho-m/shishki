package com.rubinho.shishki.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rubinho.shishki.dto.GuestDto;
import com.rubinho.shishki.dto.RegisterDto;
import com.rubinho.shishki.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class ServiceUserProviderTest {
    private ServiceUserProvider serviceUserProvider;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void init() {
        serviceUserProvider = new ServiceUserProvider(objectMapper);
    }

    @Test
    void provideTest() throws IOException {
        final GuestDto guest = GuestDto.builder()
                .name("test")
                .surname("test")
                .phone("1234")
                .email("test@mail.ru")
                .build();

        final RegisterDto first = RegisterDto.builder()
                .login("test")
                .password("test")
                .role(Role.ADMIN)
                .guest(guest)
                .build();

        final RegisterDto second = RegisterDto.builder()
                .login("test2")
                .password("test2")
                .role(Role.OWNER)
                .guest(guest)
                .build();

        assertThat(serviceUserProvider.provide())
                .hasSize(2)
                .contains(first, second);
    }
}
