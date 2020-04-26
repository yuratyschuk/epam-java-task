INSERT INTO positions
values (4, 'director', 20000, 0);
INSERT INTO positions
VALUES (2, 'operator', 15000, 0);
INSERT INTO positions
VALUES (3, 'security', 10000, 1);
INSERT INTO positions
VALUES (5, 'tester', 25000, 0);

INSERT INTO worker
values (4, 'Yura', 'Tyschuk', 2, 4, '2014-12-31');
INSERT INTO worker
values (2, 'Danil', 'Grimovsky', 1, 2, '2015-12-29');
INSERT INTO worker
values (3, 'Artur', 'Mikhailiv', 1, 3, '2015-12-31');
INSERT INTO worker
    value (5, 'Anton', 'Vakoluk', 1, 2, '2020-03-05');

INSERT INTO places
VALUES (1, 'Rivne');
INSERT INTO places
VALUES (2, 'Lviv');
INSERT INTO places
VALUES (3, 'Kyiv');
INSERT INTO places
VALUES (4, 'Ternopil');
INSERT INTO places
VALUES (5, 'Kharkiv');
INSERT INTO places
VALUES (6, 'Odesa');


INSERT INTO route
VALUES (1, 1, 2);
INSERT INTO route
VALUES (2, 3, 4);

INSERT INTO route
VALUES (3, 5, 6);
INSERT INTO route
VALUES (4, 2, 4);


INSERT INTO stop
VALUES (1, 'stop1', 11);
INSERT INTO stop
VALUES (2, 'stop2', 12);
INSERT INTO stop
VALUES (3, 'stop3', 13);
INSERT INTO stop
VALUES (4, 'stop4', 14);
INSERT INTO stop
VALUES (5, 'stop5', 15);
INSERT INTO stop
VALUES (6, 'stop6', 16);

INSERT INTO stop_route
VALUES (1, 1);
INSERT INTO stop_route
VALUES (1, 2);
INSERT INTO stop_route
VALUES (1, 3);
INSERT INTO stop_route
VALUES (1, 4);
INSERT INTO stop_route
VALUES (2, 1);
INSERT INTO stop_route
VALUES (2, 2);
INSERT INTO stop_route
VALUES (2, 3);
INSERT INTO stop_route
VALUES (2, 4);
INSERT INTO stop_route
VALUES (3, 1);
INSERT INTO stop_route
VALUES (3, 2);
INSERT INTO stop_route
VALUES (3, 3);
INSERT INTO stop_route
VALUES (3, 4);


INSERT INTO train
VALUES (2, 'train name2', 'train number2', 'PASSENGER', 10);
INSERT INTO train
VALUES (3, 'train name3', 'train number3', 'PASSENGER', 30);
INSERT INTO train
VALUES (4, 'train name4', 'train number4', 'PASSENGER', 25);
INSERT INTO train
VALUES (5, 'train name5', 'train number5', 'PASSENGER', 15);
INSERT INTO train
VALUES (6, 'train name6', 'train number6', 'PASSENGER', 20);


INSERT INTO trip
VALUES (1, '2020-11-21', '2020-11-21', 1, 250, 2, 5, 250);
INSERT INTO trip
VALUES (2, '2020-11-22', '2020-11-22', 2, 300, 3, 29, 300);
INSERT INTO trip
VALUES (3, '2020-11-23', '2020-11-23', 3, 200, 4, 25, 450);
INSERT INTO trip
VALUES (4, '2020-11-24', '2020-11-24', 4, 150, 5, 10, 200);
INSERT INTO trip
VALUES (5, '2020-11-25', '2020-11-25', 1, 350, 6, 15, 150);

INSERT INTO users
VALUES (1, 'testFirstName1', 'testLastName1', 'testUsername1', 'test1@gmail.com', 'password');
INSERT INTO users
VALUES (2, 'testFirstName2', 'testLastName2', 'testUsername2', 'test2@gmail.com', 'password');
INSERT INTO users
VALUES (3, 'testFirstName3', 'testLastName3', 'testUsername3', 'test3@gmail.com', 'password');

INSERT INTO ticket
VALUES (1, 2, '2020-08-04', 1);
INSERT INTO ticket
VALUES (2, 3, '2020-08-04', 1);
INSERT INTO ticket
VALUES (3, 1, '2020-08-04', 2);
INSERT INTO ticket
VALUES (4, 2, '2020-08-04', 2);
INSERT INTO ticket
VALUES (5, 3, '2020-08-04', 3);
INSERT INTO ticket
VALUES (6, 4, '2020-08-04', 3);
INSERT INTO ticket
VALUES (7, 5, '2020-08-04', 1);
