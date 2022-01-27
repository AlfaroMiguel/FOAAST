package com.mike.foaast.adapters.repositories;

import com.mike.foaast.domain.repositories.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class DefaultMessageRepository implements MessageRepository {

    private final WebClient webClient;
    private final String messageServiceBaseUrl;

    public DefaultMessageRepository(@Value("${application.messageServiceBaseUrl}") String messageServiceBaseUrl) {
        this.webClient = WebClient.create();
        this.messageServiceBaseUrl = messageServiceBaseUrl;
    }

    @Override
    public Mono<String> retrieveAwesomeMessage(String from) {
        final String url = String.format("%s/awesome/%s", this.messageServiceBaseUrl, from);
        return this.webClient
                .get()
                .uri(URI.create(url))
                .retrieve()
                .bodyToMono(String.class);
    }
}
