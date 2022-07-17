package ru.yandex.practicum.filmorate.sortage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserFriendDbStorage {
    private final JdbcTemplate jdbcTemplate;
    private UserDbStorage userDbStorage;

    @Autowired
    public UserFriendDbStorage(JdbcTemplate jdbcTemplate, UserDbStorage userDbStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDbStorage = userDbStorage;
    }

    public void addFriend(Integer userId, Integer friendId) {
        String sqlQuery = "INSERT INTO USER_FRIEND (user_id, friend_id)" +
                "VALUES (?, ?)";
        jdbcTemplate.update(sqlQuery,
                userId,
                friendId);
    }

    public void deleteFriends(Integer userId, Integer friendId) {
        String sqlQuery = "DELETE FROM USER_FRIEND WHERE USER_ID = ? AND FRIEND_ID = ?";
        jdbcTemplate.update(sqlQuery,
                userId,
                friendId);
    }


    public List<User> findAllFriends(Integer userId) {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT friend_id FROM USER_FRIEND " +
                "WHERE USER_ID = ?", userId);
        List<User> friends = new ArrayList<>();
        while (sqlRowSet.next()) {
            User friend = userDbStorage.findUser(sqlRowSet.getInt("friend_id"));
            friends.add(friend);
        }
        return friends;
    }


    public List<User> commonFriends(Integer userId, Integer secondUserId) {
        SqlRowSet sqlRowSetUser = jdbcTemplate.queryForRowSet("SELECT friend_id FROM USER_FRIEND " +
                "WHERE USER_ID = ?", userId);

        SqlRowSet sqlRowSetSecondUser = jdbcTemplate.queryForRowSet("SELECT friend_id FROM USER_FRIEND " +
                "WHERE USER_ID = ?", secondUserId);

        List<Integer> friendsUser = new ArrayList<>();
        List<Integer> friendsSecondUser = new ArrayList<>();

        while (sqlRowSetUser.next()) {
            friendsUser.add(sqlRowSetUser.getInt("friend_id"));
        }

        while (sqlRowSetSecondUser.next()) {
            friendsSecondUser.add(sqlRowSetSecondUser.getInt("friend_id"));
        }

        friendsUser.retainAll(friendsSecondUser);

        return friendsUser.stream().map(s -> userDbStorage.findUser(s)).collect(Collectors.toList());
    }


}
