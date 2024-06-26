#FROM ubuntu:latest
#LABEL authors="dariu"
#
#ENTRYPOINT ["top", "-b"]

#
# Build stage
#
FROM maven:3.9-sapmachine-21 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:23-jdk-oracle
COPY --from=build /home/app/target/project1-backend-spring-0.0.1-SNAPSHOT.jar /usr/local/lib/demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]


