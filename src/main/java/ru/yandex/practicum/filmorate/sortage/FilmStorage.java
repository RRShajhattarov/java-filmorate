package ru.yandex.practicum.filmorate.sortage;

import org.springframework.data.relational.core.sql.In;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.naming.InsufficientResourcesException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface FilmStorage {

    List<Film> findAll();

    Film create(Film film) throws ValidationException;

    Film put(Film film) throws ValidationException;

    //List<Integer> findAllId();

    Film findFilm(Integer id);

    void deleteFilm(Integer id);



}
