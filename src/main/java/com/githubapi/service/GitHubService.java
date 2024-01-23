package com.githubapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.githubapi.models.GitHubUser;
import com.githubapi.models.Repository;
import com.githubapi.models.UserShort;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class GitHubService {
    private static final String GITHUB_API_URL = "https://api.github.com";
    public List<UserShort> searchUsers(String query) {
        OkHttpClient client = new OkHttpClient();
        String endpoint = GITHUB_API_URL + "/search/users?q=" + query + "&per_page=10";
        System.out.println( endpoint );

        Request request = new Request.Builder()
                .url(endpoint)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseData = response.body().string();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(responseData);
                JsonNode itemsNode = root.get("items");
                List<UserShort> userList = new ArrayList<>();
                for (JsonNode item : itemsNode) {
                    String login = item.get("login").asText();
                    String url = item.get("url").asText();
                    String avatar = item.get("avatar_url").asText();
                    userList.add(new UserShort(login, url, avatar));
                }
                return userList;
            } else {
                System.out.println("Request failed with code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Repository> findAllRepositoriesByUser(String user) {

        OkHttpClient client = new OkHttpClient();
        String endpoint = GITHUB_API_URL + "/users/" + user + "/repos";

        System.out.println(endpoint);

        Request request = new Request.Builder()
                .url(endpoint)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseData = response.body().string();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(responseData);
                List<Repository> repos = new ArrayList<>();

                for (JsonNode item : root) {
                    String name = item.get("name").asText();
                    String url = item.get("html_url").asText();
                    String lang = item.get("language").asText();
                    int stargazers_count = item.get("stargazers_count").asInt();
                    int watchers_count = item.get("watchers_count").asInt();
                    int forks_count = item.get("forks_count").asInt();
                    int open_issues_count = item.get("open_issues_count").asInt();

                    repos.add(new Repository(
                            name,
                            url,
                            lang,
                            stargazers_count,
                            watchers_count,
                            forks_count,
                            open_issues_count
                    ));
                }
                return repos;
            } else {
                System.out.println("Request failed with code: " + response.code());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public GitHubUser selectUser(String user) {
        OkHttpClient client = new OkHttpClient();
        String endpoint = GITHUB_API_URL + "/users/" + user;
        System.out.println(endpoint);

        Request request = new Request.Builder()
                .url(endpoint)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseData = response.body().string();
                System.out.println(responseData);

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(responseData);

                String avatarUrl = root.get("avatar_url").asText();
                String login = root.get("login").asText();
                String name = root.get("name").asText();
                String htmlUrl = root.get("html_url").asText();
                String bio = root.get("bio").asText();
                int followers = root.get("followers").asInt();
                int following = root.get("following").asInt();
                int publicRepos = root.get("public_repos").asInt();
                String updatedAt = root.get("updated_at").asText();

                GitHubUser gitHubUser = new GitHubUser(
                        avatarUrl,
                        login,
                        name,
                        htmlUrl,
                        bio,
                        followers,
                        following,
                        publicRepos,
                        updatedAt
                );

                return gitHubUser;
            } else {
                System.out.println("Request failed with code: " + response.code());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String loadRepositoryReadMe(String user, String repo) {
        OkHttpClient client = new OkHttpClient();

        String endpoint = GITHUB_API_URL + "/repos/" + user + "/" + repo + "/readme";
        Request request = new Request.Builder()
                .url(endpoint)
                .build();
        System.out.println(endpoint);

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseData = response.body().string();
                System.out.println(responseData);

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(responseData);

                String readme = root.get("content").asText();
                return readme;
            } else {
                System.out.println("Request failed with code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

