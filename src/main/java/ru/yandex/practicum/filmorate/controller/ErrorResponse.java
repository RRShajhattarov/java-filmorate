package ru.yandex.practicum.filmorate.controller;

public class ErrorResponse {
    // название ошибки
    String error;
    // подробное описание
    String description;

    public ErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public String getDescription() {
        return description;
    }
}
