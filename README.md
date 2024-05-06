# Bookstore Management System

### Technologies used
Java 17<br>
Spring Security - JWT<br>
Hibernate JPA <br>
Liquibase - data migration tool

## Prerequisites
Before running the application, make sure you have the the following tools installed
- [Docker](https://www.docker.com/)

## Recommended Tools
For testing and interacting with the application's APIs, [Postman](https://www.postman.com/downloads/) is recommended.
Collection to all the APIs of the application are provided (see the "Calling APIs" section below)

### Building the project
./mvnw clean install

### How to run the application
- Use configuration stored in [here](intelliJ-configurations)

#### Calling APIs
API collection for postman can be found [here](postman_collection) <br>
There are two users configured for this application user/admin <br>
User can only call getAllBooks, getBookById, searchBooks APIs
Admin can call all the APIs

#### Connecting to pgAdmin
- Go to [localhost:5050](localhost:5050) in the browser to enter pgadmin login screen
    - username: admin@localost.com
    - password: password