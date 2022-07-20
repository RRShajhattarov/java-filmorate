DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS USER_FRIEND;
DROP TABLE IF EXISTS FILMS;
DROP TABLE IF EXISTS LIKE_FILMS;
DROP TABLE IF EXISTS GENRE_FILM;


-- Creating tables
CREATE TABLE if not exists USERS
(
    user_id  int auto_increment unique,
    email    varchar,
    login    varchar,
    name     varchar,
    birthday date,
    primary key (user_id)
);

CREATE TABLE if not exists FRIENDSHIP_STATUS
(
    friendship_status_id int,
    status               varchar,
    primary key (friendship_status_id)
);

CREATE TABLE if not exists USER_FRIEND
(
    user_id              int references users (user_id),
    friend_id            int references users (user_id),
    friendship_status_id int references FRIENDSHIP_STATUS (friendship_status_id)
);



CREATE TABLE if not exists MPA_RATING
(
    mpa_id      int auto_increment unique,
    mpa_rating  varchar,
    description varchar,
    primary key (mpa_id)
);


CREATE TABLE if not exists FILMS
(
    film_id      int auto_increment unique,
    name         varchar,
    description  varchar,
    release_date date,
    duration     int,
    rate         int,
    mpa_id       int references mpa_rating (mpa_id),
    primary key (film_id)
);

CREATE TABLE if not exists LIKE_FILMS
(
    user_id int references users (user_id),
    film_id int references films (film_id)
);

CREATE TABLE if not exists GENRE
(
    genre_id   int auto_increment unique,
    genre_name varchar,
    primary key (genre_id)
);

CREATE TABLE if not exists GENRE_FILM
(
    film_id  int references films (film_id),
    genre_id int references genre (genre_id)
);

MERGE INTO MPA_RATING (MPA_ID, MPA_RATING, DESCRIPTION)
    values (1, 'G', 'У фильма нет возрастных ограничений');
MERGE INTO MPA_RATING (MPA_ID, MPA_RATING, DESCRIPTION)
    values (2, 'PG', 'Детям рекомендуется смотреть фильм с родителями');
MERGE INTO MPA_RATING (MPA_ID, MPA_RATING, DESCRIPTION)
    values (3, 'PG-13', 'Детям до 13 лет просмотр не желателен');
MERGE INTO MPA_RATING (MPA_ID, MPA_RATING, DESCRIPTION)
    values (4, 'R', 'Лицам до 17 лет просматривать фильм можно только в присутствии взрослого');
MERGE INTO MPA_RATING (MPA_ID, MPA_RATING, DESCRIPTION)
    values (5, 'NC-17', 'Лицам до 18 лет просмотр запрещён');




