package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode
public class User {
    private int id;
    @NotBlank
    @Email(message = "Некоррктный email!")
    private String email;
    @NotBlank(message = "Логин не может быть пустым!")
    @Pattern(regexp = "^\\S*$")
    private String login;
    private String name;
    @Past(message = "Дата рождения не может быть в будущем!")
    private LocalDate birthday;
    private Set<Integer> friends;

    public User(int id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
        this.friends = new HashSet<>();
    }

}
