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

}
