package com.rubinho.shishki.bootstrap;

import com.rubinho.shishki.dto.RegisterDto;
import com.rubinho.shishki.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateServiceUsersRunnerTest {
    private CreateServiceUsersRunner runner;

    @Mock
    private AccountService accountService;
    @Mock
    private ServiceUserProvider serviceUserProvider;

    @BeforeEach
    void init() {
        runner = new CreateServiceUsersRunner(accountService, serviceUserProvider);
    }

    @Test
    void test() throws IOException {
        final RegisterDto first = mock();
        final RegisterDto second = mock();
        when(serviceUserProvider.provide()).thenReturn(List.of(first, second));

        runner.run();

        verify(accountService).register(first, true);
        verify(accountService).register(second, true);
    }
}
