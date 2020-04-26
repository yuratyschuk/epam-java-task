CREATE TABLE IF NOT EXISTS positions
(
    id       INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    job_name VARCHAR(40)  NOT NULL UNIQUE,
    salary   DECIMAL      NOT NULL,
    active   TINYINT      NOT NULL
);

CREATE TABLE IF NOT EXISTS worker
(
    id                 INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name         VARCHAR(40)  NOT NULL,
    last_name          VARCHAR(40)  NOT NULL,
    working_experience INT(3)       NOT NULL,
    position_id        INT UNSIGNED NOT NULL,
    hire_date          DATE         NOT NULL,

    foreign key (position_id) references positions (id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS places
(
    id   INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(40)  NOT NULL
);

CREATE TABLE IF NOT EXISTS stop
(
    id       INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(40)  NOT NULL,
    duration INT          NOT NULL
);

CREATE TABLE IF NOT EXISTS route
(
    id                 INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    departure_place_id INT UNSIGNED NOT NULL,
    arrival_place_id   INT UNSIGNED NOT NULL,

    foreign key (departure_place_id) references places (id)
        ON DELETE CASCADE,
    foreign key (arrival_place_id) references places (id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS train
(
    id                      INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    train_name              VARCHAR(40)  NOT NULL UNIQUE,
    train_number            VARCHAR(20)  NOT NULL UNIQUE,
    type                    VARCHAR(15)  NOT NULL,
    max_number_of_carriages INT UNSIGNED NOT NULL
);


CREATE TABLE IF NOT EXISTS trip
(
    id                  INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    departure_time      DATE         NOT NULL,
    arrival_time        DATE         NOT NULL,
    route_id            INT UNSIGNED NOT NULL,
    ticket_price        DECIMAL      NOT NULL,
    train_id            INT UNSIGNED,
    number_of_carriages INT UNSIGNED NOT NULL,
    number_of_places    INT UNSIGNED NOT NULL,

    FOREIGN KEY (route_id) REFERENCES route (id)
        ON DELETE CASCADE,
    FOREIGN KEY (train_id) REFERENCES train (id)
        ON DELETE SET NULL

);

CREATE TABLE IF NOT EXISTS stop_route
(
    stop_id  INT UNSIGNED NOT NULL,
    route_id INT UNSIGNED NOT NULL,

    FOREIGN KEY (stop_id) REFERENCES stop (id)
        ON DELETE CASCADE,
    FOREIGN KEY (route_id) REFERENCES route (id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS users
(
    id        INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(40)  NOT NULL,
    lastName  VARCHAR(40)  NOT NULL,
    username  VARCHAR(40)  NOT NULL,
    email     VARCHAR(40)  NOT NULL UNIQUE,
    password  VARCHAR(40)  NOT NULL

);


CREATE TABLE IF NOT EXISTS ticket
(
    id               INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    trip_id          INT UNSIGNED NOT NULL,
    time_when_bought DATETIME         NOT NULL,
    user_id          INT UNSIGNED NOT NULL,


    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (trip_id) REFERENCES trip (id)

);



