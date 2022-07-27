package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmIdNotValidation;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.sortage.GenreDbStorage;

import java.util.List;

@Service
public class GenreService {

    @Autowired
    GenreDbStorage genreDbStorage;

    public Genre findById(Integer id) {
        if (id < 0) {
            throw new FilmIdNotValidation("Id не может быть отрицательный!");
        }
        return genreDbStorage.findById(id);
    }

    public List<Genre> findAll() {
        return genreDbStorage.findAll();
    }
}
