package ru.yandex.practicum.filmorate.model;

public enum GenreEnum {
    COMEDY(1, "Комедия"),
    DRAMA(2,"Драма"),
    CARTOON(3,"Мультфильм"),
    THRILLER(4,"Триллер"),
    DOCUMENTARY(5,"Документальный"),
    ACTION(6,"Экшн");

    private int id;

    private String name;

    GenreEnum(int aId, String aName) {
        id = aId;
        name = aName;
    }

    public static String getNameById(int id) {
        for (GenreEnum o : GenreEnum.values()) {
            if (o.id == id) {
                return o.name;
            }
        }
        return "";
    }
}


