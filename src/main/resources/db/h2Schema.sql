CREATE TABLE IF NOT EXISTS positions
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
    position_id        INT UNSIGNED NOT NULL,
    hire_date          DATE,

    foreign key (position_id) references positions(id)
    ON DELETE CASCADE
);

INSERT INTO positions
values (4, 'director', 20000);
INSERT INTO positions
VALUES (2, 'operator', 15000);
INSERT INTO positions
VALUES (3, 'security', 10000);
INSERT INTO positions
VALUES (5, 'tester', 25000);

INSERT INTO worker
values (4, 'Yura', 'Tyschuk', 2, 4, '2014-12-31');
INSERT INTO worker
values (2, 'Danil', 'Grimovsky', 1, 2, '2015-12-29');
INSERT INTO worker
values (3, 'Artur', 'Mikhailiv', 1, 3, '2015-12-31');
INSERT INTO worker
value (5, 'Anton', 'Vakoluk', 1, 2, '2020-03-05');
