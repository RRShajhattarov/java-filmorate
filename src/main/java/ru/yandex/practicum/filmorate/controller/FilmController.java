package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.util.ValidationException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@RestController
public class FilmController {
    private HashMap<Integer, Film> films = new HashMap<>();
    public static Logger log = LoggerFactory.getLogger(FilmController.class);
    LocalDate minReleaseDate = LocalDate.of(1895, 12, 28);

    @GetMapping("/films")
    public Collection<Film> findAll() {
        log.debug("Текущее количество фильмов: {}", films.size());
        return films.values();
    }

    @PostMapping("/films")
    public @Valid Film create(@Valid @RequestBody Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(minReleaseDate) || film.getDuration() < 0) {
            throw new ValidationException("Некорректные данные! Дата релиза должна быть позднее 28.12.1985 года!");
        }
        log.debug("Добавлен фильм: {}", film.getName());
        if (film.getId() < 0) {
            throw new RuntimeException("Id не может быть отрицательный!");
        }
        if (film.getId() == 0) {
            film.setId(films.size() + 1);
            films.put(film.getId(), film);
        } else {
            films.put(film.getId(), film);
        }
        return film;
    }


    @PutMapping("/films")
    public @Valid Film put(@Valid @RequestBody Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(minReleaseDate) || film.getDuration() < 0) {
            throw new ValidationException("" + "Некорректные данные!");
        }

        if (film.getId() < 0) {
            throw new RuntimeException("Id не может быть отрицательный!");
        }
        films.put(film.getId(),film);
        log.debug("Изменен фильм: {}", film.getName());
        return film;
    }
}
