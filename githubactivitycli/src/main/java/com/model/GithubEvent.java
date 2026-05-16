package com.model; 

import lombok.Data;

@Data
public class GithubEvent {
    private String id;
    private String type;
    private String actorLogin;
    private String repoName;
    private String createdAt;
}
