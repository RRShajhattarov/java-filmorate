package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.sortage.FilmStorage;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import javax.validation.Valid;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    FilmService filmService;
    UserService userService;
    FilmStorage filmStorage;
    @Autowired
    public FilmController(FilmService filmService, UserService userService, FilmStorage filmStorage) {
        this.filmService = filmService;
        this.userService = userService;
        this.filmStorage = filmStorage;
    }

    @GetMapping
    public Collection<Film> findAll() {
        return filmService.findAll();
    }

    @PostMapping
    public @Valid Film create(@Valid @RequestBody Film film) throws ValidationException {
        return filmService.create(film);
    }

    @PutMapping
    public @Valid Film put(@Valid @RequestBody Film film) throws ValidationException {
        return filmService.put(film);
    }

    @PutMapping("{id}/like/{userId}")
    public void addLike(@PathVariable Integer id,
                        @PathVariable Integer userId) throws ValidationException {
        filmService.addLike(filmService.findById(id), userService.findById(userId));
    }

    @DeleteMapping("{id}/like/{userId}")
    public void deleteLike(@PathVariable Integer id,
                           @PathVariable Integer userId) throws ValidationException {
    filmService.deleteLike(filmStorage.findFilm(id), userService.findById(userId));
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularFilms(
            @RequestParam(defaultValue = "10",required = false) Integer count
            ) {
        return filmService.getPopularFilms(filmStorage.findAll(), count);
    }

}
