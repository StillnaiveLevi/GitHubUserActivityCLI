package com.dao;


import java.time.Duration;
import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import com.model.GithubEvent;

import reactor.core.publisher.Mono;

@Repository
public class GithubApiDaoImpl implements GithubApiDao {

    private final WebClient webClient;
    public GithubApiDaoImpl(WebClient.Builder webClientBuilder) {
         this.webClient = webClientBuilder
                     .baseUrl("https://api.github.com")
                     .defaultHeader("User-Agent", "GitHub-User-Activity-CLI")
                     .defaultHeader("Accept", "application/vnd.github.v3+json")
                     .build();
    }
    @Override
    public List<GithubEvent> fetchUserEvents(String username) {
        return webClient.get()
        .uri("/users/{username}/events", username)
        //.timeout(Duration.ofSeconds(10))
        .retrieve()
        .onStatus(
                HttpStatusCode::isError,
                response -> response.bodyToMono(String.class)
                        .flatMap(body -> {
                            System.err.println("GitHub API error: " + response.statusCode());
                            System.err.println("Body: " + body);

                            return Mono.error(new RuntimeException(
                                    "Failed to fetch GitHub events: " + body
                            ));
                        })
        )

        .bodyToFlux(GithubEvent.class)
        .collectList()

        .onErrorResume(e -> {
            System.err.println("Error fetching events: " + e.getMessage());
            return Mono.just(List.of());
        })

        .block();
    }
   
}
