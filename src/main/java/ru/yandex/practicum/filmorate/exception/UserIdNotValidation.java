package ru.yandex.practicum.filmorate.exception;

public class UserIdNotValidation extends RuntimeException{
    public UserIdNotValidation(String message) {
        super(message);
    }
}
