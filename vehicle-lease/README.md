Leasing application to create and manage leasing contracts.

This project demonstarates the implementation of entities, repository, service and controller layers for vehicle-lease application

Technologies used:
• Java 11
• Spring Boot 2.7.10
• MySQL Database - docker
• Gradle

Steps to run the application
----------------------------

1. Import the Gradle project in IDE after cloning or downloading the project from https://github.com/Arun-Togaf/vehicle-lease

2. Change the datasource properties in application.properties to connect to your MySQL database , 
   I have used the dockerized version of MySQL to build the application
   
   spring.jpa.hibernate.ddl-auto has been set to create. This will create a new schema from scratch based on the entities.

3. JUnit tests have been included for all the layers to test the APIs.
   The APIs can be tested by running the JUnit tests present at:
   https://github.com/Arun-Togaf/vehicle-lease/tree/development/vehicle-lease/src/test/java/com/flexi/lease

4. Swagger UI can be accessed at : 
   http://localhost:8080/swagger-ui/index.html#/

5. Postman collection with all the endpoints has been created , which can be used to test all the APIs
   Postman collection can be found at this location :
   https://github.com/Arun-Togaf/vehicle-lease/tree/development/src/test/resources/postman

6. Actuator endpoints can be accessed at :
   http://localhost:8080/vehicle-lease-actuator/
   http://localhost:8080/vehicle-lease-actuator/health
