# wallet-microservice
A simple example of a wallet microservice.

The project uses an H2 database, Liquibase, Swagger and Github Actions for CI/CD.

## Running Locally
To run, first build the project: `./gradlew clean build`
Then start up Docker: `docker-compose up --build`
Access Swagger: `http://localhost:8080/swagger-ui/`

## Calling Endpoints

# GET: /api/account/{id}
http://localhost:8080/api/account/2

This endpoint actually returns all account information, but could be modified to return just the balance if we want.

Sample response:

```
{
    "id": 2,
    "name": "Winner Today",
    "balance": 50.34
}
```

# POST: /api/account
http://localhost:8080/api/account

This endpoint creates a new account. No response is returned.

Sample JSON request:

```
{
    "id": 4,
    "name": "Poker Player"
}
```

This can be checked by calling http://localhost:8080/api/account/4

To be production ready, the ID would be created on account creation rather than sent by the client, to ensure it is unique.

# POST api/transaction
http://localhost:8080/api/transaction

This endpoint handles a new transaction request, which will either be a CREDIT to add funds or a DEBIT to take out funds.

Sample JSON request:

```
{
    "transaction_id": "0fa6b8ca-11e4-11ed-861d-0242ac120009",
    "account_id": 2,
    "amount": 100,
    "transaction_type": "CREDIT"
}
```

Sample response:

```
{
    "transaction_id": "0fa6b8ca-11e4-11ed-861d-0242ac120009",
    "success": true,
    "balance": 110.34
}
```

# POST api/transaction/list/{accountId}
http://localhost:8080/api/transaction/list/2

## Further Improvements

Add security.