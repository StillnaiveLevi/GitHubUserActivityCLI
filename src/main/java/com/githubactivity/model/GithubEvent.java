package com.githubactivity.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubEvent {
    private String id;
    private String type;

    @JsonProperty("created_at")
    private String createdAt;

    private Actor actor;
    private Repo repo;

    public String getActorLogin() {
        return actor != null ? actor.getLogin() : null;
    }

    public String getRepoName() {
        return repo != null ? repo.getName() : null;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Actor {
        private String login;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Repo {
        private String name;
    }
}
