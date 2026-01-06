package com.rubinho.shishki.bootstrap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rubinho.shishki.dto.RegisterDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceUserProviderTest {
    private ServiceUserProvider serviceUserProvider;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    void init() {
        serviceUserProvider = new ServiceUserProvider(objectMapper);
    }

    @Test
    void provideTest() throws IOException {
        final List<RegisterDto> expectedList = List.of(mock(), mock());

        when(objectMapper.readValue(any(InputStream.class), any(TypeReference.class)))
                .thenReturn(expectedList);

        assertEquals(expectedList, serviceUserProvider.provide());
    }
}
