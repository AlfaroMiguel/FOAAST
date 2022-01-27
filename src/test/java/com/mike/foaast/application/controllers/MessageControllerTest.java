package com.mike.foaast.application.controllers;

import com.mike.foaast.domain.usecases.messages.RetrieveAwesomeMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = MessageController.class)
public class MessageControllerTest {
    private static final String BASE_URL = "/api/v1/messages";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RetrieveAwesomeMessage retrieveAwesomeMessage;

    @Test
    public void testRetrieveAwesomeMessage() throws Exception {
        // TODO: continue
    }
}
