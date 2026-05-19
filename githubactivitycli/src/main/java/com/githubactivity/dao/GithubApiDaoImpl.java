package com.githubactivity.dao;


import java.time.Duration;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import com.githubactivity.exception.GithubException;
import com.githubactivity.model.GithubEvent;

import reactor.core.publisher.Mono;

@Repository
public class GithubApiDaoImpl implements GithubApiDao {
    
    private final WebClient webClient;

    
    public GithubApiDaoImpl(WebClient.Builder webClientBuilder) {
         this.webClient = webClientBuilder
                     .baseUrl("https://api.github.com")
                     .defaultHeader("User-Agent", "GitHub-User-Activity-CLI")
                     .defaultHeader("Accept", "application/vnd.github+json")
                     .build();
    }
    @Override
    public List<GithubEvent> fetchUserEvents(String username) {
        return webClient.get()
        .uri("/users/{username}/events", username)
        .retrieve()
        .onStatus(HttpStatus.NOT_FOUND::equals, 
                response -> Mono.error(
                    new GithubException("User '" + username + "' not found.")
                ))
                
        .onStatus(HttpStatus.FORBIDDEN::equals, 
                response -> Mono.error(
                    new GithubException("Rate limit exceeded. Please try again later.")
                ))
            
            // Catch other errors
        .onStatus(HttpStatusCode::isError, 
                response -> response.bodyToMono(String.class)
                    .flatMap(body -> Mono.error(
                        new GithubException("Failed to fetch activity: " + body)
                    ))
            )

        .bodyToFlux(GithubEvent.class)
        .timeout(Duration.ofSeconds(10))
        .collectList()
        .block();
    }
   
}
