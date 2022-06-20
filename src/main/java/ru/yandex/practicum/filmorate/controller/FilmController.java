package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.util.ValidationException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FilmController {
    private List<Film> films = new ArrayList<Film>();
    public static Logger log = LoggerFactory.getLogger(FilmController.class);
    LocalDate minReleaseDate = LocalDate.of(1985, 12, 28);
    @GetMapping("/films")
    public List<Film> findAll() {
        log.debug("Текущее количество фильмов: {}", films.size());
        return films;
    }

    @PostMapping("/film")
    public void create(@Valid @RequestBody Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(minReleaseDate)) {
            throw new ValidationException("Некорректные данные! Дата релиза должна быть позднее 28.12.1985 года!");
        } else {
            log.debug("Добавлен фильм: {}", film.getName());
            films.add(film);
        }
    }


    @PutMapping("/film/put")
    public void put(@Valid @RequestBody Film film) throws ValidationException {
        if(film.getName() == null || film.getName().isEmpty() || film.getReleaseDate().isBefore(minReleaseDate) || film.getDuration().isNegative()) {
            throw new ValidationException("" + "Некорректные данные!");
        }
        log.debug("Изменен фильм: {}", film.getName());
        films.add(film);
    }
}
