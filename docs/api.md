FORMAT: 1A

# API documentation
via API blueprint

# Group users

## Users [/api/users]
### Create user [POST]

+ Request (application/json)
```json
{
  "email": "string",
  "password": "string",
  "fullName": "string"
}
```

+ Response 201

### Update user [/{id}] [PUT]

+ Request (application/json)
    + Path variables
      + id - the user's id
    + Body
    ```json
    {
      "email": "string",
      "password": "string",
      "fullName": "string"
    }
    ```
+ Response 200
    + Body
    ```json
    {
      "id": "string",
      "email": "string",
      "fullName": "string"
    }
    ```

### Delete user [/{id}] [DELETE]
+ Request (application/json)
    + Path variables
        + id - the user's id

+ Response 204

### List users [/{id}] [GET]
+ Response
    + Body
    ```json
      [
        {
          "id": "string",
          "email": "string",
          "fullName": "string"
        },
        {
          "id": "string",
          "email": "string",
          "fullName": "string"
        }
      ]
    ```

### List users [GET]
+ Request (application/json)
  + Path
    + id - the user's id
+ Response
    + Body
    ```json
        {
          "id": "string",
          "email": "string",
          "fullName": "string"
        }
    ```
  
# Group accounts

## Accounts [/api/accounts]
### Create account [POST]

+ Request (application/json)
```json
{
  "ownerId": "string",
  "currency": "string",
  "amount": 0
}
```

+ Response 201

### Add money to the account [/{id}] [PUT]

+ Request (application/json)
    + Path
        + id - account id
    + Body
    ```json
        {
          "currency": "string",
          "amount": 0
        }
    ```

+ Response 204

### Get account [/{id}] [GET]

+ Request (application/json)
    + Path
        + id - account id

+ Response 200
    + Body
    ```json
      {
          "id": "string",
          "currency": "string",
          "amount": 0,
          "accountNumber": "string"
      }
    ```


### List accounts [GET]

+ Response 200
    + Body
    ```json
      [
          {
              "id": "string",
              "currency": "string",
              "amount": 0,
              "accountNumber": "string"
          },
          {
              "id": "string",
              "currency": "string",
              "amount": 0,
              "accountNumber": "string"
          }
      ]
    ```
  
# Group transactions

## Transactions  [/api/transactions]
### Create transaction [POST]

+ Request (application/json)
```json
{
  "senderAccountId": "string",
  "receiverAccountId": "string",
  "amount": 0
}
```

+ Response 204

### Revert transaction [/{id}/revert] [POST]

+ Response 204

### List all transactions [GET]
+ Response 200
```json
[
  {
    "id": "string",
    "senderAccountId": "string",
    "receiverAccountId": "string",
    "exchangeRate": "string",
    "amount": 0,
    "createdAt": "string"
  }
]
```

### Get a transactions [/{id}] [GET]
+ Request (application/json)
  + Path
    + id - account id
  
+ Response 200
```json
{
  "id": "string",
  "senderAccountId": "string",
  "receiverAccountId": "string",
  "exchangeRate": "string",
  "amount": 0,
  "createdAt": "string"
}
```

# Group loans
## Loan [/api/loans]
### Request a loan [PATCH]
+ Request (application/json)
```json
{
  "accountId": "string",
  "amount": 0
}
```

+ Response 204
