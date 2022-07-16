package ru.yandex.practicum.filmorate.sortage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserIdNotValidation;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.mapper.UserRowMapper;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UserDbStorage implements UserStorage {
    //private final HashMap<Integer, User> users = new HashMap<>();
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<User> findAll() {
        //log.debug("Текущее количество юзеров: {}", users.size());
        String sqlQuery = "select * from USERS";
        return jdbcTemplate.query(sqlQuery, new UserRowMapper());
    }


    public User put(User user) {
        log.debug("Изменен пользователь с логином: {}", user.getLogin());
        String sqlQuery = "update USERS set " +
                "EMAIL = ?, LOGIN = ?, NAME = ?, BIRTHDAY = ? " +
                "where USER_ID = ?";
        jdbcTemplate.update(sqlQuery
                , user.getEmail()
                , user.getLogin()
                , user.getName()
                , user.getBirthday()
                , user.getUserId());
        return user;
    }


    /*public List<Integer> findAllId() {

        String sqlQuery = "SELECT USER_ID FROM users";
        return new ArrayList<>(jdbcTemplate.query(sqlQuery, new UserRowMapper()));
        }*/

    public User findUser(Integer id) {
        final String sqlQuery = "select * from USERS where USER_ID = ?";
        final List<User> findUsers = jdbcTemplate.query(sqlQuery,  new UserRowMapper(), id);
        if(findUsers.isEmpty()){
            throw new UserIdNotValidation(" sdfd");
        }
        return findUsers.get(0);
    }
    @Override
    public void deleteUser(Integer id) {
        String sqlQuery = ("DELETE FROM USERS WHERE user_id = ?");
        jdbcTemplate.update(sqlQuery, id);
    }

    @Override
    public User create(User user) {
        String sqlQuery = "insert into USERS (EMAIL, LOGIN, NAME, BIRTHDAY) values (?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"USER_ID"});
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getName());
            final LocalDate birthday = user.getBirthday();
            if (birthday == null) {
                stmt.setNull(4, Types.DATE);
            } else {
                stmt.setDate(4, Date.valueOf(birthday));
            }
            return stmt;
        }, keyHolder);
        user.setUserId(keyHolder.getKey().intValue());
        return user;
    }


    public boolean containsUser(Integer id) {
        if (findUser(id) != null) {
            return true;
        } else {
            log.info("Пользователь с идентификатором {} не найден.", id);
            throw new UserIdNotValidation(String.format("Пользователь с id %d не существует.", id));
        }
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

    @Override
    public List<User> findAllFriends(Integer userId) {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT friend_id FROM USER_FRIEND " +
                "WHERE USER_ID = ?", userId);
        List<User> friends = new ArrayList<>();
        while (sqlRowSet.next()) {
            User friend = findUser(sqlRowSet.getInt("friend_id"));
            friends.add(friend);
        }
        return friends;
    }

    @Override
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

        return friendsUser.stream().map(this::findUser).collect(Collectors.toList());
    }


}
