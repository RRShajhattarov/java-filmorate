package ru.yandex.practicum.filmorate.controllerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;



@SpringBootTest
public class UserControllerTest {
    LocalDate birthday1;
    User user1;
    User user2;

    @Autowired
    UserController userController;

    @BeforeEach
    void beforeEach() {
        birthday1 = LocalDate.of(2000, 1, 1);
        user1 = new User(1,"email", "logi n1", "name1", birthday1);
        user2 = new User(2, "email2@mail.ru", "login", "", birthday1);
    }

    @Test
    void createIncorrectLogin() {
        ValidationException ex = assertThrows(ValidationException.class,
                () -> userController.create(user1));
             assertEquals("Некорректные данные! Проверьте логин или email", ex.getMessage());
    }

    @Test
    void createEmptyName() throws ValidationException {
        userController.create(user2);
        assertEquals(user2.getName(), user2.getLogin());
    }
}
