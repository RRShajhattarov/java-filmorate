package ru.yandex.practicum.filmorate.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.sortage.UserStorage;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
    this.userService = userService;
    }

    @GetMapping
    public Collection<User> findAll() {
        return userService.findAll();
    }

    @PostMapping
    public @Valid User create(@Valid @RequestBody User user) throws ValidationException {
        return userService.create(user);
    }


    @PutMapping
    public @Valid User put(@Valid  @RequestBody User user) throws ValidationException {
        return userService.put(user);
    }

    @GetMapping("{id}")
    public User getByID(@PathVariable Integer id) throws ValidationException {
        return userService.findById(id);
    }

    @PutMapping("{id}/friends/{friendId}")
    public void addFriends(
            @PathVariable Integer id,
            @PathVariable Integer friendId
    ) throws ValidationException {
        userService.addFriends(userService.findById(id), userService.findById(friendId));
    }

    @DeleteMapping("{id}/friends/{friendId}")
    public void deleteFriends(
            @PathVariable Integer id,
            @PathVariable Integer friendId
    ) throws ValidationException {
        userService.deleteFriends(userService.findById(id), userService.findById(friendId));
    }

    @GetMapping("{id}/friends")
    public Collection<User> findAllFriends(
            @PathVariable Integer id
    ) throws ValidationException {
        return userService.findAllFriends(userService.findById(id));
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> commonFriends(@PathVariable Integer id,
                                       @PathVariable Integer otherId) {
        return userService.commonFriendsList(userService.findById(id),userService.findById(otherId));
    }

    @DeleteMapping
    public @Valid void delete(@Valid  @RequestBody User user) throws ValidationException {
        userService.deleteUser(user.getUserId());
    }

}
