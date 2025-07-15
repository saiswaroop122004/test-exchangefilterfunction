package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
            .baseUrl("https://httpbin.org")
            .filter((request, next) ->
                Mono.deferContextual(ctx -> {
                    if (ctx.hasKey(ContextKeys.TEST_ENV)) {
                        String headerValue = ctx.get(ContextKeys.TEST_ENV);
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
