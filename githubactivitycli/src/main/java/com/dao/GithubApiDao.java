package com.dao;

import java.util.List;

import com.model.GithubEvent;

public interface GithubApiDao {
    List<GithubEvent> fetchUserEvents(String username);
}
