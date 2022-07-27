package ru.yandex.practicum.filmorate.model;

public enum MpaRatingEnum {
    G(1, "G"),
    PG(2, "PG"),
    PG13(3, "PG-13"),
    R(4, "R"),
    NC17(5, "NC-17");

    private int id;

    private String name;

    MpaRatingEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static String getNameById(int id) {
        for (MpaRatingEnum e : MpaRatingEnum.values()) {
            if (e.id == id) {
                return e.name;
            }
        }
        return null;
    }
}
