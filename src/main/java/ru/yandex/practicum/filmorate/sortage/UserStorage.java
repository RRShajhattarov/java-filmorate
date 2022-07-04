package ru.yandex.practicum.filmorate.sortage;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.util.Collection;
import java.util.List;

public interface UserStorage {

    Collection<User> findAll();

    User put(User user) throws ValidationException;

    User create(User user) throws ValidationException;

    List<Integer> findAllId();

    User findUser(Integer friendsId);
}
