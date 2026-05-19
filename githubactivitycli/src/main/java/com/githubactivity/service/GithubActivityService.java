package com.githubactivity.service;

import java.util.List;

import com.githubactivity.model.GithubEvent;

public interface GithubActivityService {
    List<GithubEvent> fetchUserEvents(String username);
}
