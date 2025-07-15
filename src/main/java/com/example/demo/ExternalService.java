package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ExternalService {

    private final WebClient webClient;

    public Mono<String> callExternalApi() {
        return webClient.get()
            .uri("/get")
            .retrieve()
            .bodyToMono(String.class);
    }
}
