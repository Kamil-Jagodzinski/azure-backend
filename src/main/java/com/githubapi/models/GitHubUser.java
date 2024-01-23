package com.githubapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GitHubUser {
    private String avatarUrl;
    private String login;
    private String name;
    private String htmlUrl;
    private String bio;
    private int followers;
    private int following;
    private int publicRepos;
    private String updatedAt;
}
