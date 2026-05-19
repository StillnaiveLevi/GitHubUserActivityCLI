package com.githubactivity.exception;

public class GithubException extends RuntimeException {
    private String message;

    public GithubException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
