--liquibase formatted sql

--changeset dwinn:1
CREATE TABLE transaction (
                         transaction_id int(11) NOT NULL,
                         account_id int(11) NOT NULL,
                         amount double NOT NULL,
                         transaction_type VARCHAR(32) NOT NULL,
                         PRIMARY KEY (transaction_id)
);
INSERT INTO account (transaction_id, account_id, amount, transaction_type) VALUES ("c00678b6-11d7-11ed-861d-0242ac120002", '2', 10, "DEBIT");
INSERT INTO account (transaction_id, account_id, amount, transaction_type) VALUES ("08c8b372-11db-11ed-861d-0242ac120002", '2', 24.52, "CREDIT");
INSERT INTO account (transaction_id, account_id, amount, transaction_type) VALUES ("14a44c06-11db-11ed-861d-0242ac120002", '2', 152, "CREDIT");
INSERT INTO account (transaction_id, account_id, amount, transaction_type) VALUES ("190c4316-11db-11ed-861d-0242ac120002", '2', 50, "DEBIT");
--rollback DROP TABLE transaction;
