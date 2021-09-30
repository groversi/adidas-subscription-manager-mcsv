# Ze Delivery - Location partners challenge
Ellaboreted by: Gustavo Roversi

# Description
This project handle the search of Ze Delivery partners.
There are two endpoints:
  - /partners/{id}: search partner by its is
  - /partners/geo-location: search the nearest partner by geo location
 
For more endpoints details, contracts, parameters, etc, take a look at the swagger doccumentation. 

# Features
 - MongoDB
 - Swagger: <Environment DNS>/ze-delivery/swagger-ui.html#
 - Actuator: <Environment DNS>/ze-delivery/actuator/health
 - Traceability and Observability
 
# How to run
- To start apllication locally, set JVM enviroment variable. 
```bash
 spring.profiles.active=local 
 ``` 
- As this service is a Cloud Native application, for production environments, just let the application run on default.

# Test
```bash
mvn test
```

# Deploy
```bash
docker-compose up
```

# Contact
- Email: gustavo.roversi@gmail.com
- Tel: +55 11 98555-6356


