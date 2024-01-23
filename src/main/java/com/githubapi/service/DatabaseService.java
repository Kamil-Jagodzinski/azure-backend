package com.githubapi.service;

import com.githubapi.models.LoginResponse;
import com.githubapi.models.UserShort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class DatabaseService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public LoginResponse verifyLogin(String login, String password) {
        String query = "SELECT \"userID\", \"login\" FROM \"USER\" WHERE \"login\" = ? AND \"password\" = ?";
        try {
            LoginResponse response = jdbcTemplate.queryForObject(query,
                    (rs, rowNum) -> new LoginResponse(rs.getInt("userID"), rs.getString("login")),
                    login, password);
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean createUser(String login, String password) {
        String query = "SELECT COUNT(*) FROM \"USER\" WHERE \"login\" = ?";
        int count = jdbcTemplate.queryForObject(query, Integer.class, login);

        if (count > 0) {
            return false;
        } else {
            query = "INSERT INTO \"USER\" (\"login\", \"password\") VALUES (?, ?)";
            jdbcTemplate.update(query, login, password);
            return true;
        }
    }

    public void followUser(int userId, String followingLogin, String followingUrl, String followingAvatar) {
        String query = "INSERT INTO \"FOLLOWING\" (\"user_id\", \"login\", \"url\", \"avatar\") VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(query, userId, followingLogin, followingUrl, followingAvatar);
    }

    public void unfollowUser(int userId, String followingLogin) {
        String query = "DELETE FROM \"FOLLOWING\" WHERE \"user_id\" = ? AND \"login\" = ?";
        jdbcTemplate.update(query, userId, followingLogin);
    }

    public List<UserShort> getFollowingUsers(int userId) {
        String query = "SELECT \"login\", \"url\", \"avatar\" FROM \"FOLLOWING\" WHERE \"user_id\" = ?";
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            UserShort userShort = new UserShort();
            userShort.setLogin(rs.getString("login"));
            userShort.setUrl(rs.getString("url"));
            userShort.setAvatar(rs.getString("avatar"));
            return userShort;
        }, userId);
    }
}
