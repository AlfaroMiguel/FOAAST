package com.example.foaast.application.controllers;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import static org.springframework.util.MimeTypeUtils.TEXT_HTML_VALUE;

import com.example.foaast.domain.usecases.messages.GetAwesomeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/messages")
public class MessageController {
    private static Logger LOGGER = LoggerFactory.getLogger(MessageController.class);
    private final GetAwesomeMessage getAwesomeMessage;

    public MessageController(GetAwesomeMessage getAwesomeMessage) {
        this.getAwesomeMessage = getAwesomeMessage;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/awesome/{from}", produces = TEXT_HTML_VALUE)
    Mono<String> getAwesomeMessage(
        @PathVariable final String from,
        @RequestHeader String userId) { // TODO: pass a JWT token and take the userId from there.
            LOGGER.info("GET /api/v1/messages/awesome/{} with userId: {}", from, userId);
            return getAwesomeMessage.get(); // TODO: add response type and mapper.
    }
}
