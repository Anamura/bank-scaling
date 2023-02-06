INSERT INTO users(id, name, date_of_birth, password)
VALUES (6789, 'Bill Gates', '1955-10-28', '1234'),
       (1234, 'Chris Richardson', '1962-12-09', 'pwd'),
       (2345, 'Elvis Presley', '1935-01-08', 'check'),
       (7624, 'John Legend',  '1978-12-28', 'ee');

INSERT INTO account(id, balance, user_id, version)
VALUES (24, 100, 6789, 1), (31, 100, 1234, 1), (56, 100, 2345, 1), (48, 100, 7624, 1);

INSERT INTO email_data(id, email, user_id)
VALUES (45, 'bgate@microsoft.com', 6789), (87, 'chric@pivotal.com', 1234), (64, 'epresley@music.com', 2345), (12, 'jlegtn@music.com', 7624);

INSERT INTO phone_data(id, phone, user_id)
VALUES (52, 19456371289, 6789), (17, 79207865432, 1234), (18, 24514236877, 2345), (74, 22683421916, 7624);
