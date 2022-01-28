package com.mike.foaast.application.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.mike.foaast.application.controllers.TestConstants.VALID_STATUS_PING_RESPONSE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StatusController.class)
public class StatusControllerTest {
    private static final String BASE_URL = "/api/v1/status";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testStatusPing() throws Exception {
        final String pingUrl = String.format("%s/ping", BASE_URL);

        final MvcResult mvcResult = mockMvc.perform(
                        get(pingUrl)
                ).andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(result -> {
                    String responseString = result.getResponse().getContentAsString();
                    assertThat(responseString).isNotNull();
                    assertThat(responseString).isEqualTo(VALID_STATUS_PING_RESPONSE);
                });
    }
}
