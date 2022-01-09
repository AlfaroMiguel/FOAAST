package com.example.foaast.application.controllers;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

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

    @RequestMapping(method = RequestMethod.GET, path = "/awesome/{from}", produces = APPLICATION_JSON_VALUE)
    Mono<Void> getAwesomeMessage(
        @PathVariable final String from,
        @RequestHeader String userId) {
            LOGGER.info("GET /api/v1/messages/awesome/{} with userId: {}", from, userId);
            return Mono.empty();
    }
}
