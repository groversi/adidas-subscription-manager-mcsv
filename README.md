# Adidas subscription manager 
Ellaboreted by: Gustavo Roversi

# Description
This project handle the subscription process of Adidas newsletter.
Basically, it does 4 process:
 - Create a subscription
 - List a subscription by id
 - List all subscriptions
 - Cancel a subscription

# Features
 - MongoDB
 - Swagger: <Environment DNS>/adidas/swagger-ui.html#
 - Actuator: <Environment DNS>/adidas/actuator/health
 - Traceability and Observability
 - Docker compatible
 - Azure compatible

#Endpoints
 - POST: <Environment DNS>/adidas/v1/subscription - Create a subscription
 - GET:  <Environment DNS>/adidas/v1/subscription/{subscriptionID} - Get a subscription by Id
 - GET:  <Environment DNS>/adidas/v1/subscription - List all subscriptions
 - DELETE: <Environment DNS>/adidas/v1/subscription - Logically delete a subscription

PS: All endpoints needs a JWS to authorize it access. These JWS are generated by the adidas-backend-for-frontend-mcsv service.
Please, read the service documentation.

For more details, see the swagger documentation. (On the repo is also included a Postman collection).

#Dependencies
This project needs other projects do work properly. Please download and run the other projects:
- adidas-backend-for-frontend-mcsv
- adidas-email-server-mcsv

For more doubts, reade README section of these projects.
 
# How to run
- To start application locally, set JVM environment variable and run Docker compose for mongoDb services. 
```bash
 spring.profiles.active=local 
 ``` 
- As this service is a Cloud Native application, for production environments, just let the application run on default.

# Test
 - ArchUnit tests
 - Integration tests
 - Unit tests

```bash
mvn test
```

# Deploy
```bash
docker-compose up
```

# CI/CD
This project brings compatibility with kubernetes environment through GitHub actions. 
Take a look at the .git folder and the manifests' folder. 

It's necessary to adjust variables to your environment.

# Contact
- Email: gustavo.roversi@gmail.com
- Tel: +55 11 98555-6356


