package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmIdNotValidation;
import ru.yandex.practicum.filmorate.exception.FilmNotExistsException;
import ru.yandex.practicum.filmorate.exception.UserIdNotValidation;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.LikeFilms;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.sortage.FilmStorage;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.sortage.LikeFilmDbStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FilmService {
    FilmStorage filmStorage;
    LikeFilmDbStorage likeFilmDbStorage;
    LocalDate minReleaseDate = LocalDate.of(1895, 12, 28);

    @Autowired
    public FilmService (FilmStorage filmStorage, LikeFilmDbStorage likeFilmDbStorage) {
        this.filmStorage = filmStorage;
        this.likeFilmDbStorage = likeFilmDbStorage;
    }

    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film create(Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(minReleaseDate) || film.getDuration() < 0) {
            throw new FilmNotExistsException("Некорректные данные! Дата релиза должна быть позднее 28.12.1985 года!");
        }
        if (film.getFilmId() < 0) {
            throw new FilmIdNotValidation("Id не может быть отрицательный!");
        }
        return filmStorage.create(film);
    }

    public Film put(Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(minReleaseDate) || film.getDuration() < 0) {
            throw new FilmNotExistsException("" + "Некорректные данные!");
        }
        if (film.getFilmId() < 0) {
            throw new FilmIdNotValidation("Id не может быть отрицательный!");
        }
        return filmStorage.put(film);
    }

    public Film findById(Integer id) {
        return filmStorage.findFilm(id);
    }

    public void addLike(Film film, User user) {
        if(user.getUserId() < 0) {
            throw new UserIdNotValidation("Id не может быть отрицательный!");
        }
        if(film.getFilmId() < 0) {
            throw new FilmIdNotValidation("Id не может быть отрицательный!");
        }

        likeFilmDbStorage.addLike(user.getUserId(), film.getFilmId());
    }

    public void deleteLike(Film film, User user) {
        if(user.getUserId() < 0) {
            throw new UserIdNotValidation("Id не может быть отрицательный!");
        }
        if(film.getFilmId() < 0) {
            throw new FilmIdNotValidation("Id не может быть отрицательный!");
        }
        likeFilmDbStorage.deleteLike(user.getUserId(), film.getFilmId());
    }

    public List<Film> getPopularFilms(Integer count) {
        List<LikeFilms> popularFilms = likeFilmDbStorage.getPopularFilms(count);
        List<Film> likeFilmIds = new ArrayList<>();
        popularFilms.forEach(f -> {
            Film film = findById(f.getFilmId());
            likeFilmIds.add(film);
        });
        return likeFilmIds;
    }

    public void DeleteFilm(Film film) {
        filmStorage.deleteFilm(film.getFilmId());
    }


}
