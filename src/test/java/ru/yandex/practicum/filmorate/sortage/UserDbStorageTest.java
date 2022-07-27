package ru.yandex.practicum.filmorate.sortage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.UserIdNotValidation;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserDbStorageTest {

    private final UserDbStorage userStorage;

    @Test
    void findAll() {
        Optional<List<User>> optionalUsers = Optional.of(List.copyOf(userStorage.findAll()));

        assertThat(optionalUsers.get().size()).isEqualTo(3);

    }

    @Test
    void create() {
        User user = new User();
        user.setUserId(3);
        user.setName("test1");
        user.setEmail("test1@test1.ru");
        user.setLogin("test1");
        user.setBirthday(LocalDate.of(1999, 01, 01));

        userStorage.create(user);

        assertThat(user).hasFieldOrPropertyWithValue("userId",3);
    }

    @Test
    void put() {
        User user = new User();
        user.setUserId(2);
        user.setName("test1Changed");
        user.setEmail("test1Changed@test1.ru");
        user.setLogin("test1Changed");
        user.setBirthday(LocalDate.of(1998, 01, 01));
        userStorage.put(user);


        assertThat(user).hasFieldOrPropertyWithValue("name", "test1Changed");
    }

    @Test
    void findUser() {
        User user = userStorage.findUser(1);

        assertThat(user).hasFieldOrPropertyWithValue("userId", 1);

    }

    @Test
    void deleteUser() {
        userStorage.deleteUser(3);
        assertThrows(UserIdNotValidation.class ,() -> userStorage.findUser(3));
    }
}