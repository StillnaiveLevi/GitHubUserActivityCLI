# GitHub User Activity CLI

A simple Spring Boot command-line application that shows the recent public GitHub activity for a given username.

The app calls the GitHub Events API, reads the user's public events, and prints a short summary in the terminal.

## Features

- Accepts a GitHub username from the command line
- Fetches recent public activity from GitHub
- Handles common errors such as missing users and rate limits
- Formats activity into readable messages, for example:
  - `Pushed commits to torvalds/linux at 2026-05-19T15:04:06Z`
  - `Starred username/repository at 2026-05-19T12:00:00Z`

## Requirements

- Java 17 or newer
- Internet connection
- Maven is not required globally because the project includes Maven Wrapper

## How to Run

Open a terminal in the project folder:

```powershell
cd "C:\Users\HP\OneDrive\Desktop\GitHubUserActivityCLI\githubactivitycli"
```

Run the app with a GitHub username:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.arguments=torvalds"
```

Replace `torvalds` with any GitHub username.

## Example Output

```text
Recent activity for user: torvalds
- Pushed commits to torvalds/linux at 2026-05-19T15:04:06Z
- Created something in torvalds/GuitarPedal at 2026-05-13T22:43:20Z
```

If no username is provided, the app prints:

```text
Please provide a GitHub username as an argument.
```

## Project Structure

```text
src/main/java/com/githubactivity
├── controller   # Runs the CLI command when the app starts
├── dao          # Calls the GitHub API using WebClient
├── exception    # Custom GitHub exception class
├── model        # Java model for GitHub event data
├── service      # Handles business logic and formats output
└── WebClientConfig.java
```

## Main Classes

- `GithubActivityCommandRunner` starts the command-line flow.
- `GithubApiDaoImpl` sends the HTTP request to GitHub.
- `GithubActivityImpl` prints the user's activity in a readable format.
- `GithubEvent` maps the JSON response from GitHub.

## Common Issues

If you see this error:

```text
Failed to resolve 'api.github.com'
```

It usually means your computer cannot reach GitHub because of an internet, DNS, VPN, or proxy problem. Check your connection and try again.

If you see a rate limit error, wait and try again later. GitHub limits unauthenticated API requests.

## Build and Test

Run the tests with:

```powershell
.\mvnw.cmd test
```
