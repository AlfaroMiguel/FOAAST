package com.mike.foaast.adapters.repositories;

import com.mike.foaast.domain.repositories.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class DefaultMessageRepository implements MessageRepository {
    private static Logger LOGGER = LoggerFactory.getLogger(DefaultMessageRepository.class);

    @Override
    public Mono<String> retrieveAwesomeMessage(String from) {

        final String url = String.format("https://foaas.com/awesome/%s", from); // TODO: read from env vars.

        return WebClient.create()
                .get()
                .uri(URI.create(url))
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> LOGGER.info("Successfully retrieved an awesome message from {}", from))
                .doOnError(e -> LOGGER.info("Failed to retrieve an awesome message from {}", from, e));
    }
}
