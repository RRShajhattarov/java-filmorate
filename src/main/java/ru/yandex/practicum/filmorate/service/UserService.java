package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserIdNotValidation;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.sortage.UserFriendDbStorage;
import ru.yandex.practicum.filmorate.sortage.UserStorage;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class UserService {
    UserStorage userStorage;
    UserFriendDbStorage userFriendDbStorage;


    @Autowired
    public UserService (UserStorage userStorage, UserFriendDbStorage userFriendDbStorage) {
        this.userStorage = userStorage;
        this.userFriendDbStorage = userFriendDbStorage;
    }

    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    public User put(User user) throws ValidationException {
        if(user.getLogin().contains(" ")) {
            throw new ValidationException("Некорректные данные! Логин содержит пробелы");
        }
        if(user.getUserId() <= 0) {
            throw new UserIdNotValidation("Id не может быть отрицательный!");
        }
        if (user.getName().isEmpty() || user.getName() == null) {
            user.setName(user.getLogin());
        }
        return userStorage.put(user);
    }

    public User create(User user) throws ValidationException {
        if (user.getLogin().contains(" ") ) {
            throw new ValidationException("Некорректные данные! Проверьте логин или email");
        }
        if(user.getUserId() < 0) {
            throw new UserIdNotValidation("Id не может быть отрицательный!");
        }
        if (user.getName().isEmpty() || user.getName() == null) {
            user.setName(user.getLogin());
        }
        return userStorage.create(user);
    }

    public User findById(Integer id) throws UserIdNotValidation {
        return userStorage.findUser(id);
    }

    public void addFriends(User user, User friends) throws ValidationException {
        if(!(findAll().contains(user) && findAll().contains(friends))) {
            throw new ValidationException("Такого пользователя не существует");
        }
        userFriendDbStorage.addFriend(user.getUserId(), friends.getUserId());
    }

    public void deleteFriends(User user, User friends) {
        userFriendDbStorage.deleteFriends(user.getUserId(), friends.getUserId());
    }

    public List<User> findAllFriends(User user) {
        if (user == null) {
            throw new UserIdNotValidation("Такого пользователя не существует");
        }
        return userFriendDbStorage.findAllFriends(user.getUserId());

    }

    public List<User> commonFriendsList(User user1, User user2) {
        if(user1.getUserId() < 0) {
            throw new UserIdNotValidation("Id не может быть отрицательный!");
        }
        if(user2.getUserId() < 0) {
            throw new UserIdNotValidation("Id не может быть отрицательный!");
        }
        return userFriendDbStorage.commonFriends(user1.getUserId(), user2.getUserId());
    }

    public void deleteUser(Integer id) {
        userStorage.deleteUser(id);
    }

}
