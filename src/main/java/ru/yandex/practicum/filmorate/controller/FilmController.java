package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FilmController {
    private List<Film> films = new ArrayList<Film>();
    public static Logger log = LoggerFactory.getLogger(FilmController.class);

    @GetMapping("/films")
    public List<Film> findAll() {
        log.info("Выведены все фильмы");
        return films;
    }

    @PostMapping("/film")
    public void create(@RequestBody Film film) {
        films.add(film);
    }


}
