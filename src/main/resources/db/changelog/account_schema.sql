--liquibase formatted sql

--changeset dwinn:1
CREATE TABLE account (
                      account_id int(11) NOT NULL,
                      account_name VARCHAR(200) NOT NULL,
                      balance double NOT NULL,
                      PRIMARY KEY (account_id)
);
INSERT INTO account (account_id, account_name, balance) VALUES (1, 'Money Spinner', 0);
INSERT INTO account (account_id, account_name, balance) VALUES (2, 'Winner Today', 50.34);
INSERT INTO account (account_id, account_name, balance) VALUES (3, 'Pro Player', 1000);
--rollback DROP TABLE account;
