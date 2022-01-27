package com.mike.foaast.application.controllers;

import static org.springframework.util.MimeTypeUtils.TEXT_HTML_VALUE;

import com.mike.foaast.domain.usecases.messages.RetrieveAwesomeMessage;
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
    private final RetrieveAwesomeMessage retrieveAwesomeMessage;

    public MessageController(RetrieveAwesomeMessage retrieveAwesomeMessage) {
        this.retrieveAwesomeMessage = retrieveAwesomeMessage;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/awesome/{from}", produces = TEXT_HTML_VALUE)
    Mono<String> retrieveAwesomeMessage(
        @PathVariable final String from,
        @RequestHeader String userId) {
        LOGGER.info("Awesome message requested from {} with userId {}", from, userId);
        return retrieveAwesomeMessage.retrieve(from);
    }
}
