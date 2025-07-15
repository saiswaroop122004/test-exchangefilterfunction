package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class DemoController {

    private final ExternalService externalService;

    @GetMapping("/test-call")
    public Mono<ResponseEntity<String>> testCall(
            @RequestHeader(name = "X-Test-Env", required = false) String env) {

        return Mono.defer(() -> externalService.callExternalApi())
                .map(ResponseEntity::ok)
                .contextWrite(ctx -> {
                    if (env != null && !env.isBlank()) {
                        return ctx.put(ContextKeys.TEST_ENV, env);
                    }
                    return ctx;
                });
    }
}
