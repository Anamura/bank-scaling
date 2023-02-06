## Реализация сервиса "Bank Scaling".

В системе можно:
1. Взаимодействовать с пользователем менять/удалить email и phone.
2. Осуществить трансфер денег от одного пользователя к другому.
3. BALANCE клиента увеличиваются на 10% от начального депозита.
4. Поиск пользователей с пагинацией и фильтрацией.

Стек технологий:

Spring Boot, Lombok, Spring Data, Spring Validation, RabbitMQ, Redis, Postgres db.

#### Build

    $ ./mvn clean install -Dmaven.test.skip

    $  java -jar target/bank-scaling-1.0.2-SNAPSHOT.jar


Swagger Spec

http://localhost:8091/swagger-ui.html
![swagger2](https://user-images.githubusercontent.com/5726929/216921759-8f07e42a-409a-479a-ba33-8cf50ef26708.png)

#### Usage


-- curl http://localhost:8091/api/v1/account/{accountId}  get Account

-- curl http://localhost:8091/api/v1/account/transfer/{accountId}  transfer Balance

-- curl http://localhost:8091/api/v1/user/{userId=1}/update  update User

-- curl http://localhost:8091/api/v1/user  search Users


      $ curl -H "Accept: application/json" GET http://localhost:8091/api/v1/account/{accountId}
    {
      "id": 1,
      "balance": 100
    }

      $ curl -H "Accept: application/json" -H "Authorization: Bearer {token}" GET 
      http://localhost:8091/api/v1/account/transfer/2
    {
      "from": 1,
      "to": 2,
      "value": 100
    }
    
      $ PUT -H "Accept: application/json" http://localhost:8091/api/v1/user/{userId=1}/update
    {
      "name": "Chris Richardson",
      "date_of_birth": 28.10.1955, 
      "email": ["chric@pivotal.com"]
      "phone": ["79207865432"]
    }
    
    $ GET http://localhost:8091/api/v1/user?dateOfBirth=28.12.1984&name=Bill&page=0&size=20
    [  {
         "name": "Elvis Presley",
         "phone": 24514236877
       },
       {
         "name": "John Legend",
         "phone": 22683421916
       }
     ]

    $ POST http://localhost:8091/api/v1/user/{userId}/email  add email
    
    $ GET http://localhost:8091/api/v1/user/{userId}/phone   get phone
