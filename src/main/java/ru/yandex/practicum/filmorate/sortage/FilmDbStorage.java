package ru.yandex.practicum.filmorate.sortage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.model.mapper.FilmRowMapper;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


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
        List<Genre> genre = film.getGenre();
        if (genre != null) {
            genre.forEach(g -> jdbcTemplate.update("INSERT INTO GENRE_FILM values (?,?)", film.getFilmId(), g.getId()));
        }
        MpaRating mpa = film.getMpa();
        if (mpa != null) {
            jdbcTemplate.update("INSERT INTO MPA_FILMS values (?,?)",mpa.getId(), film.getFilmId());
        }

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
        if (film.getMpa() != null) {
            jdbcTemplate.update("update mpa_films set mpa_id = ? where film_id = ?",
                    film.getMpa().getId(), film.getFilmId());
        }

        if (film.getGenre() != null && !film.getGenre().isEmpty()) {
            film.getGenre().forEach(g -> {
                List<Integer> integers = jdbcTemplate.query("select film_id from genre_film where film_id = ? and genre_id = ?", new RowMapper<Integer>() {
                    @Override
                    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getInt("FILM_ID");
                    }
                }, film.getFilmId(), g.getId());
                if (integers.isEmpty()) {
//                    jdbcTemplate.update("delete from genre_film where film_id = ?", film.getFilmId());
                    jdbcTemplate.update("insert into genre_film values(?,?)",film.getFilmId(), g.getId());
                } else {
                    jdbcTemplate.update("update genre_film set genre_id = ? where film_id = ? and genre_id = ?",
                            g.getId(), film.getFilmId(), g.getId());
                }});
            List<Integer> genreIds = film.getGenre().stream().map(Genre::getId).collect(Collectors.toList());
            List<Integer> forDelInts = getGenre(genreIds);
            forDelInts.forEach(i -> {
                jdbcTemplate.update("delete from genre_film where film_id = ? and genre_id = ?", film.getFilmId(), i);
            });


        } else if (film.getGenre() != null && film.getGenre().isEmpty()) {
            jdbcTemplate.update("delete from genre_film where film_id = ?", film.getFilmId());
        }

        return findFilm(film.getFilmId());
    }

    private List<Integer> getGenre(List<Integer> genreIds) {
        List<Integer> genres = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6));
        genres.removeAll(genreIds);
        return genres;
    }

    @Override
    public Film findFilm(Integer id) {
        final String sqlQuery = "select * from FILMS where FILM_ID = ?";
        final List<Film> findFilms = jdbcTemplate.query(sqlQuery,  new FilmRowMapper(), id);
        if (findFilms.isEmpty()) {
            throw new NoSuchElementException();
        }
        Film film = findFilms.get(0);
        List<Integer> query = jdbcTemplate.query("select MPA_ID from MPA_FILMS WHERE FILM_ID = ?",
                (rs, rowNum) -> rs.getInt("MPA_ID"), film.getFilmId());

        MpaRating mpa = new MpaRating();
        film.setMpa(mpa);

        if (query.isEmpty()) {
            return film;
        }

        int mpaId = query.get(0);
        mpa.setId(mpaId);
        mpa.setName(MpaRatingEnum.getNameById(mpaId));

        List<Genre> genreList = new ArrayList<>();
        film.setGenre(genreList);
        List<Integer> genreId = jdbcTemplate.query("select genre_id from genre_film where film_id = ?",
                (rs, rowNum) -> rs.getInt("GENRE_ID"),
                film.getFilmId());
        genreId.forEach(g -> {
            Genre genre = new Genre();
            genre.setId(g);
            genre.setName(GenreEnum.getNameById(g));
            genreList.add(genre);
        });
        return film;
    }

    @Override
    public void deleteFilm(Integer id) {
        String sqlQuery = "DELETE FROM FILMS WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }


}
