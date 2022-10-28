# Welcome to ZAccount - A Bank Account Demo

This project was built with Maven Java Spring Boot, Java 17.
It connects to an in memory H2 database, accessible via the default endpoint /h2-console. Username and password are kept as the default and can be accessed via .properties.

This API was tested via Postman, default port 8080.

Retrieve balance

GET /account/balance with Query Params accountNum & pin

POST /account/withdrawal Body example:
    {
        "accountNum": 987654321,
        "pin": "4321",
        "withdrawalAmount": 500
}

```
mvn clean package
```

