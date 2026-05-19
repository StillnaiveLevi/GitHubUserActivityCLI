package com.githubactivity.dao;

import java.util.List;

import com.githubactivity.model.GithubEvent;



public interface GithubApiDao {
    List<GithubEvent> fetchUserEvents(String username);
}
