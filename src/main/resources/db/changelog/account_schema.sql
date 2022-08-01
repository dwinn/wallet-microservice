--liquibase formatted sql

--changeset dwinn:1
CREATE TABLE account (
                      id int(11) NOT NULL,
                      name VARCHAR(200) NOT NULL,
                      balance double NOT NULL,
                      PRIMARY KEY (id)
);
INSERT INTO account (id, name, balance) VALUES (1, 'Money Spinner', 23);
INSERT INTO account (id, name, balance) VALUES (2, 'Winner Today', 50.34);
INSERT INTO account (id, name, balance) VALUES (3, 'Pro Player', 1000);
--rollback DROP TABLE account;
