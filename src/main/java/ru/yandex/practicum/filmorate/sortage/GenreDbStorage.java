package ru.yandex.practicum.filmorate.sortage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.GenreEnum;

import java.util.List;

@Slf4j
@Service
public class GenreDbStorage {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Genre findById(int id) {
        List<Genre> genres = jdbcTemplate.query("select * from genre where genre_id = ?", (rs, rowNum) -> {
            Genre genre = new Genre();
            genre.setId(rs.getInt("GENRE_ID"));
            genre.setName(GenreEnum.getNameById(genre.getId()));
            return genre;
        }, id);
        return genres.size() != 1 ? new Genre() : genres.get(0);
    }

    public List<Genre> findAll() {
        List<Genre> genres = jdbcTemplate.query("select genre_id from genre",
                (rs, rowNum) -> {
                    Genre genre = new Genre();
                    genre.setId(rs.getInt("GENRE_ID"));
                    genre.setName(GenreEnum.getNameById(genre.getId()));
                    return genre;
                });
        return genres;
    }
}
