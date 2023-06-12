-- Inserting screening rooms
INSERT INTO screening_room (name) VALUES ('Room 1');
INSERT INTO screening_room (name) VALUES ('Room 2');
INSERT INTO screening_room (name) VALUES ('Room 3');
INSERT INTO screening_room (name) VALUES ('Room 4');
INSERT INTO screening_room (name) VALUES ('Room 5');
INSERT INTO screening_room (name) VALUES ('Room 6');


INSERT INTO movie (title, duration, description) VALUES ('Movie 1', 120, 'Description 1');
INSERT INTO movie (title, duration, description) VALUES ('Movie 2', 90, 'Description 2');
INSERT INTO movie (title, duration, description) VALUES ('Movie 3', 105, 'Description 3');
INSERT INTO movie (title, duration, description) VALUES ('Movie 4', 150, 'Description 4');
INSERT INTO movie (title, duration, description) VALUES ('Movie 5', 100, 'Description 5');
INSERT INTO movie (title, duration, description) VALUES ('Movie 6', 130, 'Description 6');


INSERT INTO screening (movie_id, room_id, start_time, end_time)
VALUES (1, 1, '2025-12-31 23:59:59', '2026-01-01 01:59:59');

INSERT INTO screening (movie_id, room_id, start_time, end_time)
VALUES (2, 1, '2025-12-31 18:00:00', '2026-01-01 00:00:00');

INSERT INTO screening (movie_id, room_id, start_time, end_time)
VALUES (2, 2, '2025-12-31 18:00:00', '2026-01-01 19:30:00');

INSERT INTO screening (movie_id, room_id, start_time, end_time)
VALUES (3, 2, '2026-01-01 20:00:00', '2026-01-01 22:00:00');

INSERT INTO screening (movie_id, room_id, start_time, end_time)
VALUES (1, 3, '2025-12-31 20:00:00', '2026-01-01 22:00:00');

INSERT INTO screening (movie_id, room_id, start_time, end_time)
VALUES (3, 3, '2022-01-01 15:00:00', '2026-01-01 16:45:00');

INSERT INTO screening (movie_id, room_id, start_time, end_time)
VALUES (4, 4, '2022-01-01 12:00:00', '2026-01-01 14:30:00');

INSERT INTO screening (movie_id, room_id, start_time, end_time)
VALUES (5, 5, '2022-12-31 16:00:00', '2026-01-01 18:30:00');

INSERT INTO screening (movie_id, room_id, start_time, end_time)
VALUES (6, 6, '2022-12-31 10:00:00', '2026-01-01 12:30:00');

INSERT INTO seat (room_id, seat_number) VALUES (1, 'A1');
INSERT INTO seat (room_id, seat_number) VALUES (1, 'A2');
INSERT INTO seat (room_id, seat_number) VALUES (1, 'A3');
INSERT INTO seat (room_id, seat_number) VALUES (1, 'A4');
INSERT INTO seat (room_id, seat_number) VALUES (2, 'B1');
INSERT INTO seat (room_id, seat_number) VALUES (2, 'B2');
INSERT INTO seat (room_id, seat_number) VALUES (2, 'B3');
INSERT INTO seat (room_id, seat_number) VALUES (2, 'B4');
