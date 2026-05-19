package com.githubactivity.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.githubactivity.dao.GithubApiDao;
import com.githubactivity.exception.GithubException;
import com.githubactivity.model.GithubEvent;

@Service
public class GithubActivityImpl implements GithubActivityService {
    private final GithubApiDao githubApiDao;

    public GithubActivityImpl(GithubApiDao githubApiDao) {
        this.githubApiDao = githubApiDao;
    }

    @Override
    public List<GithubEvent> fetchUserEvents(String username) {
        try {
            List<GithubEvent> events = githubApiDao.fetchUserEvents(username);
            if (events.isEmpty()) {
                System.out.println("No recent activity found for user: " + username);
            } else {
                System.out.println("Recent activity for user: " + username);
                events.forEach(event -> System.out.println("- " + formatEvent(event) + formatCreatedAt(event)));
            }
            return events;
        } catch (GithubException e) {
            System.err.println("Error fetching user events: " + e.getMessage());
            return List.of();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            return List.of();
        }
    }

    private String formatEvent(GithubEvent event) {
        String repoName = event.getRepoName() != null && !event.getRepoName().isEmpty()
                ? event.getRepoName()
                : "unknown repository";

        if (event.getType() == null) {
            return "Unknown activity in " + repoName;
        }

        return switch (event.getType()) {
            case "PushEvent" -> "Pushed commits to " + repoName;
            case "WatchEvent" -> "Starred " + repoName;
            case "IssuesEvent" -> "Opened an issue in " + repoName;
            case "IssueCommentEvent" -> "Commented on an issue in " + repoName;
            case "PullRequestEvent" -> "Opened a pull request in " + repoName;
            case "PullRequestReviewEvent" -> "Reviewed a pull request in " + repoName;
            case "ForkEvent" -> "Forked " + repoName;
            case "CreateEvent" -> "Created something in " + repoName;
            case "DeleteEvent" -> "Deleted something in " + repoName;
            case "ReleaseEvent" -> "Published a release in " + repoName;
            case "PublicEvent" -> "Made " + repoName + " public";
            case "MemberEvent" -> "Added a collaborator to " + repoName;
            default -> event.getType() + " in " + repoName;
        };
    }

    private String formatCreatedAt(GithubEvent event) {
        return event.getCreatedAt() != null && !event.getCreatedAt().isEmpty()
                ? " at " + event.getCreatedAt()
                : "";
    }
}
