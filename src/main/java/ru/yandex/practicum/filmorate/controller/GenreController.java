package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    GenreService genreService;

    @GetMapping("{id}")
    public Genre findById(@NotNull @PathVariable Integer id){
        return genreService.findById(id);
    }

    @GetMapping
    public List<Genre> findAll() {
        return genreService.findAll();
    }
}
