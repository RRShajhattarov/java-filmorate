package ru.yandex.practicum.filmorate.model.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.LikeFilms;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LikeFilmsRowMapper implements RowMapper<LikeFilms> {

    @Override
    public LikeFilms mapRow(ResultSet rs, int rowNum) throws SQLException {
        LikeFilms likeFilms = new LikeFilms(rs.getInt("FILM_ID"));
        return likeFilms;
    }
}
