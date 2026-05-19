package com.githubactivity.controller;

import org.springframework.stereotype.Component;

import com.githubactivity.service.GithubActivityService;

@Component
public class GithubActivityCommandRunner implements org.springframework.boot.CommandLineRunner {

    private final GithubActivityService githubActivityService;

    public GithubActivityCommandRunner(GithubActivityService githubActivityService) {
        this.githubActivityService = githubActivityService;
    }

    @Override
    public void run(String... args) {

        if (args.length == 0) {
            System.out.println("Please provide a GitHub username as an argument.");
            return;
        }

        String username = args[0].trim();
        if (username.isEmpty()) {
            System.out.println("Username cannot be empty. Please provide a valid GitHub username.");
            return;
        }
        githubActivityService.fetchUserEvents(username);
    }
}
