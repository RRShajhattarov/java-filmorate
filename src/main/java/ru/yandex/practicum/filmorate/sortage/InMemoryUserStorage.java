package ru.yandex.practicum.filmorate.sortage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.util.*;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage{
    private final HashMap<Integer, User> users = new HashMap<>();

    public Collection<User> findAll()  {
        log.debug("Текущее количество юзеров: {}", users.size());
        return users.values();
    }

    public User put(User user) {
        log.debug("Изменен пользователь с логином: {}", user.getLogin());
        users.put(user.getId(),user);
        return user;
    }

    public User create(User user) throws ValidationException {
        log.debug("Добавлен пользователь с логином: {}", user.getLogin());
        if(user.getId() == 0) {
            user.setId(users.size()+1);
        }
        users.put(user.getId(), user);
        return user;
    }

    public List<Integer> findAllId() {
        return new ArrayList<>(users.keySet());
    }

    public User findUser(Integer id) {
        return users.get(id);
    }


}
