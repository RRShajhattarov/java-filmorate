package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmIdNotValidation;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.sortage.MpaDbStorage;

import java.util.List;

@Service
public class MpaService {

    @Autowired
    MpaDbStorage mpaDbStorage;

    public MpaRating findById(Integer id) {
        if (id < 0) {
            throw new FilmIdNotValidation("Id не может быть отрицательный!");
        }
        return mpaDbStorage.findById(id);
    }

    public List<MpaRating> findAll() {
        List<MpaRating> mpaRatingList = mpaDbStorage.findAll();
        return mpaRatingList;
    }

}
