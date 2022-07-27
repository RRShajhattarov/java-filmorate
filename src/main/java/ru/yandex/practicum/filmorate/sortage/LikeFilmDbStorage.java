package ru.yandex.practicum.filmorate.sortage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.model.MpaRatingEnum;
import ru.yandex.practicum.filmorate.model.mapper.FilmRowMapper;

import java.util.List;

@Service
public class LikeFilmDbStorage {
    @Autowired
    private JdbcTemplate jdbcTemplate;



    public boolean addLike(Integer userId, Integer filmId) {
        int updatedRows = jdbcTemplate.update("INSERT INTO LIKE_FILMS values(?, ?)", userId, filmId);
        return updatedRows > 0;
    }


    public void deleteLike(Integer userId, Integer filmId) {
        final String sqlQuery = "DELETE FROM LIKE_FILMS where USER_ID = ? AND FILM_ID = ?";
        jdbcTemplate.update(sqlQuery,
                userId, filmId);
    }


    public List<Film> getPopularFilms(Integer count) {
        List<Film> films = jdbcTemplate.query("SELECT * FROM FILMS AS f " +
                        "LEFT JOIN LIKE_FILMS AS fl ON f.film_id = fl.film_id GROUP BY f.name ORDER BY COUNT(user_id) DESC LIMIT ?",
                new FilmRowMapper(), count);
        films.forEach(f -> {
            List<Integer> query = jdbcTemplate.query("select MPA_ID from MPA_FILMS WHERE FILM_ID = ?",
                    (rs, rowNum) -> rs.getInt("MPA_ID"), f.getFilmId());

            MpaRating mpa = new MpaRating();
            f.setMpa(mpa);

            int mpaId = query.get(0);
            mpa.setId(mpaId);
            mpa.setName(MpaRatingEnum.getNameById(mpaId));
        });
        return films;
    }
}
