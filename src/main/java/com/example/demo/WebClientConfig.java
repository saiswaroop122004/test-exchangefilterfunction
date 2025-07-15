package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;
import lombok.extern.slf4j.Slf4j; // Add this line


@Configuration
@Slf4j
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
            .baseUrl("https://httpbin.org")
            .filter((request, next) ->
                Mono.deferContextual(ctx -> {
                    if (ctx.hasKey(ContextKeys.TEST_ENV)) {
                        String headerValue = ctx.get(ContextKeys.TEST_ENV);
                        log.info("Adding header X-Test-Env with value: {}", headerValue);
                        ClientRequest updatedRequest = ClientRequest.from(request)
                            .header(ContextKeys.TEST_ENV, headerValue)
                            .build();
                        return next.exchange(updatedRequest);
                    }
                    return next.exchange(request);
                })
            )
            .build();
    }
}
