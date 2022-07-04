package ru.yandex.practicum.filmorate.sortage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage{
    private final HashMap<Integer, Film>  films = new HashMap<>();


    @Override
    public Collection<Film> findAll() {
        log.debug("Текущее количество фильмов: {}", films.size());
        return films.values();
    }

    @Override
    public Film create(Film film) {
        log.debug("Добавлен фильм: {}", film.getName());
        if (film.getId() == 0) {
            film.setId(films.size() + 1);
            films.put(film.getId(), film);
        } else {
            films.put(film.getId(), film);
        }
        return film;
    }

    @Override
    public Film put(Film film) {
        films.put(film.getId(),film);
        log.debug("Изменен фильм: {}", film.getName());
        return film;
    }

    @Override
    public List<Integer> findAllId() {
        return new ArrayList<>(films.keySet());
    }

    @Override
    public Film findFilm(Integer id) {
        return films.get(id);
    }


}
