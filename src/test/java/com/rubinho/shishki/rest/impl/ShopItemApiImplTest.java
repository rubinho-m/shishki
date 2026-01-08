package com.rubinho.shishki.rest.impl;

import com.rubinho.shishki.services.ShopItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShopItemApiImpl.class)
@AutoConfigureMockMvc(addFilters = false)
public class ShopItemApiImplTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ShopItemService shopItemService;

    @Test
    void getAllTest() throws Exception {
        mockMvc.perform(get("/api/v2/shop")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
