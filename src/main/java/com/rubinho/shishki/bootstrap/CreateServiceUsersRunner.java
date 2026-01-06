package com.rubinho.shishki.bootstrap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rubinho.shishki.dto.RegisterDto;
import com.rubinho.shishki.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@Profile("init")
public class CreateServiceUsersRunner implements CommandLineRunner {
    private final AccountService accountService;
    private final ServiceUserProvider serviceUserProvider;

    @Autowired
    public CreateServiceUsersRunner(AccountService accountService,
                                    ServiceUserProvider serviceUserProvider) {
        this.accountService = accountService;
        this.serviceUserProvider = serviceUserProvider;
    }

    @Override
    public void run(String... args) throws IOException {
        final List<RegisterDto> serviceUsers = serviceUserProvider.provide();
        for (RegisterDto serviceUser: serviceUsers) {
            accountService.register(serviceUser, true);
        }
    }
}
