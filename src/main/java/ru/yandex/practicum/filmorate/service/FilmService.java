package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmIdNotValidation;
import ru.yandex.practicum.filmorate.exception.FilmNotExistsException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.sortage.FilmStorage;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService {
    FilmStorage filmStorage;
    LocalDate minReleaseDate = LocalDate.of(1895, 12, 28);

    @Autowired
    public FilmService (FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film create(Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(minReleaseDate) || film.getDuration() < 0) {
            throw new FilmNotExistsException("Некорректные данные! Дата релиза должна быть позднее 28.12.1985 года!");
        }
        if (film.getId() < 0) {
            throw new FilmIdNotValidation("Id не может быть отрицательный!");
        }
        return filmStorage.create(film);
    }

    public Film put(Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(minReleaseDate) || film.getDuration() < 0) {
            throw new FilmNotExistsException("" + "Некорректные данные!");
        }
        if (film.getId() < 0) {
            throw new FilmIdNotValidation("Id не может быть отрицательный!");
        }
        return filmStorage.put(film);
    }

    public Film findById(Integer id) throws ValidationException {
        if (!filmStorage.findAllId().contains(id)) {
            throw new FilmIdNotValidation("Некорректные данные! Проверьте логин или email");
        }
        return filmStorage.findFilm(id);
    }

    public void addLike(Film film, User user) {
        Set<Integer> filmLikes = film.getLikes();
        filmLikes.add(user.getId());
        film.setLikes(filmLikes);
    }

    public void deleteLike(Film film, User user) {
        Set<Integer> filmLikes = film.getLikes();
        filmLikes.remove(user.getId());
        film.setLikes(filmLikes);
    }

    public Collection<Film> getPopularFilms(Collection<Film> listFilms, Integer count) {
        return listFilms.stream()
                .sorted((o1, o2) -> o2.getLikes().size() - o1.getLikes().size())
                .limit(count).collect(Collectors.toList());
    }




}
