package ru.yandex.practicum.filmorate.sortage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.mapper.FilmRowMapper;

import java.util.Collection;
import java.util.List;

@Component
public class LikeFilmDbStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LikeFilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void addLike(Integer userId, Integer filmId) {
        final String sqlQuery = "INSERT INTO LIKE_FILMS (USER_ID, FILM_ID) values(?, ?)";
        jdbcTemplate.update(sqlQuery,
                userId, filmId);
    }


    public void deleteLike(Integer userId, Integer filmId) {
        final String sqlQuery = "DELETE FROM LIKE_FILMS where USER_ID = ? AND FILM_ID = ?";
        jdbcTemplate.update(sqlQuery,
                userId, filmId);
    }


    public List<Film> getPopularFilms(Integer count) {
        String sqlQuery = "select FILM_ID, count(USER_ID) as LIKES from LIKE_FILMS " +
                "GROUP BY FILM_ID ORDER BY LIKES DESC LIMIT ?";
        return jdbcTemplate.query(sqlQuery, new FilmRowMapper(), count);
    }
}
