CREATE TABLE IF NOT EXISTS position
(
    id       INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    job_name VARCHAR(40),
    salary   DECIMAL
);

CREATE TABLE IF NOT EXISTS worker
(
    id                 INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name         VARCHAR(40),
    last_name          VARCHAR(40),
    working_experience INT(3),
    position_id        INT NOT NULL,
    hire_date          DATE,

    foreign key (position_id) references position (id)
);

INSERT INTO position
values (4, 'Director', 20000);
INSERT INTO position
VALUES (2, 'Operator', 15000);
INSERT INTO position
VALUES (3, 'Security', 10000);

INSERT INTO worker
values (4, 'Yura', 'Tyschuk', 2, 4, '2014-12-31');
INSERT INTO worker
values (2, 'Danil', 'Grimovsky', 1, 2, '2015-12-29');
INSERT INTO worker
values (3, 'Artur', 'Mikhailiv', 1, 3, '2015-12-31');
