--liquibase formatted sql

--changeset dwinn:1
CREATE TABLE IF NOT EXISTS transaction (
                         transaction_id VARCHAR(256) NOT NULL,
                         account_id int(11) NOT NULL,
                         amount double NOT NULL,
                         transaction_type VARCHAR(32) NOT NULL,
                         PRIMARY KEY (transaction_id)
);
INSERT INTO transaction (transaction_id, account_id, amount, transaction_type) VALUES ('c00678b6-11d7-11ed-861d-0242ac120002', '2', 10, 'DEBIT');
INSERT INTO transaction (transaction_id, account_id, amount, transaction_type) VALUES ('08c8b372-11db-11ed-861d-0242ac120002', '2', 24.52, 'CREDIT');
INSERT INTO transaction (transaction_id, account_id, amount, transaction_type) VALUES ('14a44c06-11db-11ed-861d-0242ac120002', '2', 152, 'CREDIT');
INSERT INTO transaction (transaction_id, account_id, amount, transaction_type) VALUES ('190c4316-11db-11ed-861d-0242ac120002', '2', 50, 'DEBIT');
INSERT INTO transaction (transaction_id, account_id, amount, transaction_type) VALUES ('c5c191a2-11df-11ed-861d-0242ac120002', '1', 100, 'CREDIT');
INSERT INTO transaction (transaction_id, account_id, amount, transaction_type) VALUES ('ca0a6cfc-11df-11ed-861d-0242ac120002', '1', 41.32, 'DEBIT');
INSERT INTO transaction (transaction_id, account_id, amount, transaction_type) VALUES ('cf61d88e-11df-11ed-861d-0242ac120002', '3', 80, 'CREDIT');
INSERT INTO transaction (transaction_id, account_id, amount, transaction_type) VALUES ('d607374c-11df-11ed-861d-0242ac120002', '3', 15.20, 'DEBIT');
--rollback DROP TABLE transaction;
