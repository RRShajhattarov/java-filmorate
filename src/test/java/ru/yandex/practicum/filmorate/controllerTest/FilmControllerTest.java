package ru.yandex.practicum.filmorate.controllerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.FilmNotExistsException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class FilmControllerTest {

        LocalDate releaseDate;
        Film film1;

        long duration1;

        @Autowired
        FilmController filmController;

        @BeforeEach
        void beforeEach() {
            duration1 = 100;
            releaseDate = LocalDate.of(1825, 1, 1);
            film1 = new Film(1,"name", "logi n sdad",  releaseDate, duration1, 4);
        }

        @Test
        void createDateReleaseMore1985Years() {
            FilmNotExistsException ex = assertThrows(FilmNotExistsException.class,
                    () -> filmController.create(film1));
            assertEquals("Некорректные данные! Дата релиза должна быть позднее 28.12.1985 года!", ex.getMessage());
        }
}
