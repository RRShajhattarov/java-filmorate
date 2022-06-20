package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;
import java.time.LocalDate;
import javax.validation.constraints.*;


@Data
public class Film {
    private int id;
    @NotBlank(message = "Имя не может быть пустым!")
    private String name;
    @NotBlank(message = "Описание не может быть пустым!")
    @Size(max = 200)
    private String description;
    @Past(message = "Некорректная дата релиза")
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность не может быть отрицательной!")
    private Duration duration;

    public Film(int id, String name, String description, LocalDate releaseDate, Duration duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}
