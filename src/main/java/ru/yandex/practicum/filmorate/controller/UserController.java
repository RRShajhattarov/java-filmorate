package ru.yandex.practicum.filmorate.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.util.ValidationException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private List<User> users = new ArrayList<User>();
    public static Logger log = LoggerFactory.getLogger(FilmController.class);

    @GetMapping("/users")
    public List<User> findAll() {
        log.debug("Текущее количество юзеров: {}", users.size());
        return users;
    }

    @PostMapping("/user")
    public void create(@Valid @RequestBody User user) throws ValidationException {
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Некорректные данные! Проверьте логин или email");
        }
        if (user.getName().isEmpty() || user.getName() == null) {
            user.setName(user.getLogin());
        }
        log.debug("Добавлен пользователь с логином: {}", user.getLogin());
        users.add(user);
    }


    @PutMapping("/user/put")
    public void put(@Valid  @RequestBody User user) throws ValidationException {
        if(user.getLogin().contains(" ")) {
            throw new ValidationException("Некорректные данные! Логин содержит пробелы");
        }
        if (user.getName().isEmpty() || user.getName() == null) {
            user.setName(user.getLogin());
        }
        log.debug("Изменен пользователь с логином: {}", user.getLogin());
        users.add(user);
    }

}
