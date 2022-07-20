package ru.yandex.practicum.filmorate.sortage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmDbStorageTest {

    private final FilmDbStorage filmDbStorage;
    @Test
    void findAll() {
        List<Film> films = filmDbStorage.findAll();

        assertFalse(films.isEmpty());
    }

    @Test
    void create() {
        Film film = new Film();

        film.setName("Фильм1");
        film.setDescription("Описание фильма 1");
        film.setReleaseDate(LocalDate.of(2000, 01, 01));
        film.setDuration(100);
        film.setRate(10);

        MpaRating mpaRating = new MpaRating();
        mpaRating.setId(1);

        film.setMpa(mpaRating);

        filmDbStorage.create(film);

        assertThat(film).hasFieldOrPropertyWithValue("filmId", 2);
    }

    @Test
    void put() {
        Film film = new Film();

        film.setFilmId(2);
        film.setName("Фильм измененный 1");
        film.setDescription("Описание фильма измененного 1");
        film.setReleaseDate(LocalDate.of(2000, 01, 01));
        film.setDuration(100);
        film.setRate(10);

        MpaRating mpaRating = new MpaRating();
        mpaRating.setId(2);

        film.setMpa(mpaRating);

        filmDbStorage.put(film);

        assertThat(film).hasFieldOrPropertyWithValue("description", "Описание фильма измененного 1");
    }

    @Test
    void findFilm() {
        Film film = filmDbStorage.findFilm(1);

        assertThat(film).hasFieldOrPropertyWithValue("filmId", 1);
    }

    @Test
    void deleteFilm() {
        filmDbStorage.deleteFilm(2);

        List<Film> films = filmDbStorage.findAll();

        assertThat(films.size()).isEqualTo(1);
    }
}