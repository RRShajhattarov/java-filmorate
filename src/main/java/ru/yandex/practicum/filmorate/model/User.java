package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @JsonProperty("id")
    private int userId;
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
    private FriendshipStatus friendshipStatus;

    public User(int userId, String email, String login, String name, LocalDate birthday, FriendshipStatus friendshipStatus) {
        this.userId = userId;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
        this.friends = new HashSet<>();
        this.friendshipStatus = friendshipStatus;
    }

    public User(int userId, String email, String login, String name, LocalDate birthday) {
        this.userId = userId;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
}
