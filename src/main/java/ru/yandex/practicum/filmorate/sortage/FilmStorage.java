package ru.yandex.practicum.filmorate.sortage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.util.Collection;
import java.util.List;

public interface FilmStorage {

    Collection<Film> findAll();

    Film create(Film film) throws ValidationException;

    Film put(Film film) throws ValidationException;

    List<Integer> findAllId();

    Film findFilm(Integer id);

}
