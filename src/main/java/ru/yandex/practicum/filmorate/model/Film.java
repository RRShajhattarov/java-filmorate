package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Film {
    @JsonProperty("id")
    private int filmId;
    @NotBlank(message = "Имя не может быть пустым!")
    private String name;
    @NotBlank(message = "Описание не может быть пустым!")
    @Size(max = 200)
    private String description;
    @Past(message = "Некорректная дата релиза")
    private LocalDate releaseDate;
    private long duration;
    //private Set<Integer> likes;
    private int rate;
    private long likes;
    private Genre genre;
    @NotNull
    private MpaRating mpa;

    public Film(int filmId, String name, String description, LocalDate releaseDate, long duration, int rate,  MpaRating mpa) {
        this.filmId = filmId;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.rate = rate;
        this.mpa = mpa;
    }

    public Film(int filmId, String name, String description, LocalDate releaseDate, long duration, int rate) {
        this.filmId = filmId;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}
