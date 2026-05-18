package com.controller;

import com.service.GithubActivityService;

public class GithubActivityCommandRunner implements CommandLineRunner {

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

        String username = args[0];
        if (username.isEmpty()) {
            System.out.println("Username cannot be empty. Please provide a valid GitHub username.");
            return;
        }
        githubActivityService.fetchUserEvents(username);
    }

}
