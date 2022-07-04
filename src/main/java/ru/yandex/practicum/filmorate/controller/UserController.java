package ru.yandex.practicum.filmorate.controller;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.util.ValidationException;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private HashMap<Integer, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> findAll() {
        log.debug("Текущее количество юзеров: {}", users.size());
        return users.values();
    }

    @PostMapping
    public @Valid User create(@Valid @RequestBody User user) throws ValidationException {
        if (user.getLogin().contains(" ") ) {
            throw new ValidationException("Некорректные данные! Проверьте логин или email");
        }
        if(user.getId() < 0) {
            throw new RuntimeException("Id не может быть отрицательный!");
        }
        if (user.getName().isEmpty() || user.getName() == null) {
            user.setName(user.getLogin());
        }
        log.debug("Добавлен пользователь с логином: {}", user.getLogin());


        if(user.getId() == 0) {
            user.setId(users.size()+1);
            users.put(user.getId(), user);
        }
        else {
            users.put(user.getId(), user);
        }
        return user;
    }


    @PutMapping
    public @Valid User put(@Valid  @RequestBody User user) throws ValidationException {
        if(user.getLogin().contains(" ")) {
            throw new ValidationException("Некорректные данные! Логин содержит пробелы");
        }
        if(user.getId() <= 0) {
            throw new RuntimeException("Id не может быть отрицательный!");
        }
        if (user.getName().isEmpty() || user.getName() == null) {
            user.setName(user.getLogin());
        }
        log.debug("Изменен пользователь с логином: {}", user.getLogin());

        users.put(user.getId(),user);
        return user;
    }

}
