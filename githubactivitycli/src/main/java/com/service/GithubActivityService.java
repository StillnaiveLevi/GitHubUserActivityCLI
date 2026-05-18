package com.service;

import java.util.List;

import com.model.GithubEvent;

public interface GithubActivityService {
    List<GithubEvent> fetchUserEvents(String username);
}
