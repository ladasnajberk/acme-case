ACME Application
============================

> Example application

### Major code directories layout
    .    
    ├── src/main/java/com.acme/customer         # customer service
    ├── src/main/java/com.acme/fileparser       # fileparser service
    └── src/main/java/com.acme/productfee       # productfee service
   

### How to build
./gradlew build

docker-compose up --build

###How to start in IDEA
Start database using
'docker-compose up --build'

start AcmeApplication
