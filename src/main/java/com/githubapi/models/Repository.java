package com.githubapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Repository {
    private String name;
    private String html_url;
    private String language;
    private int stargazers_count;
    private int watchers_count;
    private int forks_count;
    private int open_issues_count;
}
