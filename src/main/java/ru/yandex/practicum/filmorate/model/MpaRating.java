package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MpaRating {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    @JsonIgnore
    private String name;

}
