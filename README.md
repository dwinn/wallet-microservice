# Wallet Microservice
A simple example of a wallet microservice.

The project uses an H2 database, Liquibase, Swagger and Github Actions for CI/CD.

## Running Locally
To run, first build the project: `./gradlew clean build`

Then start up Docker: `docker-compose up --build`

Access Swagger: `http://localhost:8080/swagger-ui/`

Database GUI: `http://localhost:8080/h2-console` Login with URL: `jdbc:h2:~/data/demo`, username `sa`, password `password`.

## Calling Endpoints

### GET: /api/account/{id}
http://localhost:8080/api/account/2

This endpoint actually returns all account information, but could be modified to return just the balance if we want.

Sample response:

```json
{
    "id": 2,
    "name": "Winner Today",
    "balance": 50.34
}
```

### PUT: /api/account
http://localhost:8080/api/account

This endpoint creates a new account. No response is returned.

Sample JSON request:

```json
{
    "id": 4,
    "name": "Poker Player"
}
```

This can be checked by calling http://localhost:8080/api/account/4

To be production ready, the ID would be created on account creation rather than sent by the client, to ensure it is unique.

### POST api/transaction
http://localhost:8080/api/transaction

This endpoint handles a new transaction request, which will either be a CREDIT to add funds or a DEBIT to take out funds.

Sample JSON request:

```json
{
    "transaction_id": "0fa6b8ca-11e4-11ed-861d-0242ac120009",
    "account_id": 2,
    "amount": 100,
    "transaction_type": "CREDIT"
}
```

Sample response:

```json
{
    "transaction_id": "0fa6b8ca-11e4-11ed-861d-0242ac120009",
    "success": true,
    "balance": 110.34
}
```

### POST api/transaction/list/{accountId}
http://localhost:8080/api/transaction/list/2

This request provides a list of transactions for a given account ID.

Sample JSON response:

```json
[
    {
        "transaction_id": "c00678b6-11d7-11ed-861d-0242ac120002",
        "account_id": 2,
        "amount": 10.0,
        "transaction_type": "DEBIT"
    },
    {
        "transaction_id": "08c8b372-11db-11ed-861d-0242ac120002",
        "account_id": 2,
        "amount": 24.52,
        "transaction_type": "CREDIT"
    }
]
```

## Further Improvements

Add security to requests.

Create account ID on backend rather than passed in.

Use Enum for transaction type. Seems H2 database has issues with enums.