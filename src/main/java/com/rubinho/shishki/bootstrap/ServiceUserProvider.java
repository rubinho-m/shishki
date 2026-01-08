package com.rubinho.shishki.bootstrap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rubinho.shishki.dto.RegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class ServiceUserProvider {
    private final ObjectMapper objectMapper;

    @Autowired
    public ServiceUserProvider(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<RegisterDto> provide() throws IOException {
        final ClassPathResource resource = new ClassPathResource("service-users.json");
        try (InputStream inputStream = resource.getInputStream()) {
            return objectMapper.readValue(
                    inputStream,
                    new TypeReference<>() {
                    }
            );
        }
    }
}
