package com.mike.foaast.application.controllers;

import com.mike.foaast.domain.usecases.messages.RetrieveAwesomeMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import reactor.core.publisher.Mono;
import reactor.test.publisher.PublisherProbe;

import static com.mike.foaast.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MessageController.class)
public class MessageControllerTest {
    private static final String BASE_URL = "/api/v1/messages";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RetrieveAwesomeMessage retrieveAwesomeMessage;

    @Test
    public void testRetrieveAwesomeMessage() throws Exception {
        final String retrieveAwesomeMessageUrl = String.format("%s/awesome/%s", BASE_URL, VALID_FROM_NAME);
        PublisherProbe<String> publisherProbe = PublisherProbe.of(Mono.just(VALID_AWESOME_MESSAGE));

        when(this.retrieveAwesomeMessage.retrieve(VALID_FROM_NAME))
                .thenReturn(publisherProbe.mono());

        final MvcResult mvcResult = mockMvc.perform(
                get(retrieveAwesomeMessageUrl)
                        .header(USER_ID_HEADER_NAME, VALID_USER_ID)
                ).andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML_VALUE))
                .andExpect(result -> {
                    String responseString = result.getResponse().getContentAsString();
                    assertThat(responseString).isNotNull();
                    assertThat(responseString).isEqualTo(VALID_AWESOME_MESSAGE);
                });

        assertThat((publisherProbe).wasSubscribed()).isTrue();
        verify(this.retrieveAwesomeMessage).retrieve(VALID_FROM_NAME);
    }
}
