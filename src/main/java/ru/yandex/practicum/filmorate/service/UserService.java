package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserIdNotValidation;
import ru.yandex.practicum.filmorate.model.User;
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

    @Autowired
    public UserService (UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    public User put(User user) throws ValidationException {
        if(user.getLogin().contains(" ")) {
            throw new ValidationException("Некорректные данные! Логин содержит пробелы");
        }
        if(user.getId() <= 0) {
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
        if(user.getId() < 0) {
            throw new UserIdNotValidation("Id не может быть отрицательный!");
        }
        if (user.getName().isEmpty() || user.getName() == null) {
            user.setName(user.getLogin());
        }
        return userStorage.create(user);
    }

    public User findById(Integer id) throws UserIdNotValidation {
        if (!userStorage.findAllId().contains(id)) {
            throw new UserIdNotValidation("Некорректные данные! Такого id не существует");
        }
        return userStorage.findUser(id);
    }

    public void addFriends(User user, User friends) {

        Set<Integer> userList = user.getFriends();
        userList.add(friends.getId());
        user.setFriends(userList);

        Set<Integer> friendsList = friends.getFriends();
        friendsList.add(user.getId());
        friends.setFriends(friendsList);
    }

    public void deleteFriends(User user, User friends) {

        Set<Integer> userList = user.getFriends();
        userList.remove(friends.getId());
        user.setFriends(userList);

        Set<Integer> friendsList  = friends.getFriends();
        friendsList.remove(user.getId());
        friends.setFriends(friendsList);
    }

    public List<User> findAllFriends(User user) {
        List<User> friends = new ArrayList<>();
        List<Integer> friendsId = new ArrayList<>(user.getFriends());
        for (Integer integer : friendsId) {
            friends.add(userStorage.findUser(integer));
        }
        return  friends;
    }

    public List<User> commonFriendsList(User user1, User user2) {
        Set<Integer> setFriendsUser1 = user1.getFriends();
        Set<Integer> setFriendsUser2 = user2.getFriends();

        List<User> commonFriends = new ArrayList<>();

        for (Integer l : setFriendsUser1) {
            for (Integer s : setFriendsUser2) {
                if (l.equals(s)) {
                    commonFriends.add(userStorage.findUser(l));
                }
            }
        }
        return commonFriends;
    }


}
