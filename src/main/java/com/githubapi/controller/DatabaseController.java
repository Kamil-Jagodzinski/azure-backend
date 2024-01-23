package com.githubapi.controller;

import com.githubapi.models.LoginResponse;
import com.githubapi.models.User;
import com.githubapi.models.UserShort;
import com.githubapi.service.DatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/users")
public class DatabaseController {
    private final DatabaseService databaseService;

    @PostMapping("/create")
    public boolean createUser(@RequestBody User user) {
        return databaseService.createUser(user.getLogin(), user.getPassword());
    }
    @GetMapping("/verify")
    public LoginResponse verifyLogin(@RequestParam String login, @RequestParam String password) {
        return databaseService.verifyLogin(login, password);
    }
    @PostMapping("/follow/{userId}")
    public void followUser(@PathVariable int userId, @RequestBody UserShort userShort) {
        databaseService.followUser(userId, userShort.getLogin(), userShort.getUrl(), userShort.getAvatar());
    }
    @DeleteMapping("/unfollow/{userId}/{login}")
    public void unfollowUser(@PathVariable int userId, @PathVariable String login) {
        databaseService.unfollowUser(userId, login);
    }
    @GetMapping("/{userId}/following")
    public List<UserShort> getFollowingUsers(@PathVariable int userId) {
        return databaseService.getFollowingUsers(userId);
    }
}
