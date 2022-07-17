package ru.yandex.practicum.filmorate.sortage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.mapper.FilmRowMapper;
import ru.yandex.practicum.filmorate.model.mapper.UserRowMapper;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


@Slf4j
@Service
public class FilmDbStorage implements FilmStorage{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Film> findAll() {
        String sqlQuery = "select * from FILMS";
        return jdbcTemplate.query(sqlQuery, new FilmRowMapper());
    }

    @Override
    public Film create(Film film) {
        String sqlQuery = "insert into FILMS (NAME, DESCRIPTION, RELEASE_DATE, DURATION) values (?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"FILM_ID"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setLong(4, film.getDuration());
            final LocalDate releaseDate = film.getReleaseDate();
            if (releaseDate == null) {
                stmt.setNull(3, Types.DATE);
            } else {
                stmt.setDate(3, Date.valueOf(releaseDate));
            }
            return stmt;
        }, keyHolder);
        film.setFilmId(keyHolder.getKey().intValue());
        return film;
    }

    @Override
    public Film put(Film film) {
        String sqlQuery = "update FILMS set " +
                "NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION  = ? " +
                "where FILM_ID = ?";
        jdbcTemplate.update(sqlQuery
                , film.getName()
                , film.getDescription()
                , film.getReleaseDate()
                , film.getDuration()
                , film.getFilmId());
        return film;
    }

    @Override
    public Film findFilm(Integer id) {
        final String sqlQuery = "select * from FILMS where FILM_ID = ?";
        final List<Film> findFilms = jdbcTemplate.query(sqlQuery,  new FilmRowMapper(), id);
        return findFilms.get(0);
    }

    @Override
    public void deleteFilm(Integer id) {
        String sqlQuery = "DELETE FROM FILMS WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }


}
