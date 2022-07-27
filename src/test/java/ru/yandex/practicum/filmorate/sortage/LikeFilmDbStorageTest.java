package ru.yandex.practicum.filmorate.sortage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.LikeFilms;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class LikeFilmDbStorageTest {

    private final LikeFilmDbStorage likeFilmDbStorage;
    private final FilmDbStorage filmDbStorage;

    @Test
    void addLike() {
        assertTrue(likeFilmDbStorage.addLike(1,1));
    }

    @Test
    void deleteLike() {
        likeFilmDbStorage.deleteLike(2, 1);

        Film film = filmDbStorage.findFilm(1);

        assertThat(film.getLikes()).isEqualTo(0);
    }

    @Test
    void getPopularFilms() {
        List<LikeFilms> popularFilms = likeFilmDbStorage.getPopularFilms(1);

        assertFalse(popularFilms.isEmpty());
    }
}