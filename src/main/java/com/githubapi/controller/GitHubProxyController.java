package com.githubapi.controller;

import com.githubapi.models.GitHubUser;
import com.githubapi.models.Repository;
import com.githubapi.models.UserShort;
import com.githubapi.service.GitHubService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/github")
public class GitHubProxyController {
    private final GitHubService gitHubProxyService;
    @GetMapping("/users/{user}/repos")
    public List<Repository> loadRepositories(@PathVariable String user) {
        List<Repository> repositories = gitHubProxyService.findAllRepositoriesByUser(user);
        return repositories;
    }
    @GetMapping("/repos/{user}/{repo}/readme")
    public String  loadReadme(@PathVariable String user, @PathVariable String repo) {
        String readme = gitHubProxyService.loadRepositoryReadMe(user, repo);
        return readme;
    }
    @GetMapping("/users/{user}")
    public GitHubUser selectUser(@PathVariable String user) {
        GitHubUser gitHubUser = gitHubProxyService.selectUser(user);
        return gitHubUser;
    }
    @GetMapping("/users/search/{query}")
    public List<UserShort> searchUsers(@PathVariable String query) {
        List<UserShort> users = gitHubProxyService.searchUsers(query);
        return users;
    }
}
