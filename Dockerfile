FROM openjdk:11

RUN apt-get update && apt-get install -y maven
EXPOSE 8080
COPY . /project
RUN  cd /project && mvn package

#run the spring boot application
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar","/project/target/adidas-subscription-manager-mcsv-0.0.1.jar"]