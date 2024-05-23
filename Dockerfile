#FROM ubuntu:latest
#LABEL authors="dariu"
#
#ENTRYPOINT ["top", "-b"]

#
# Build stage
#
FROM maven:3.9.2-jdk-21 AS build

# Create a directory for the app
WORKDIR /app

# Copy and list src directory
COPY src /app/src
RUN ls -l /app/src

# Copy and list pom.xml
COPY pom.xml /app
RUN ls -l /app/pom.xml

# Run Maven build
RUN mvn -f /app/pom.xml clean package

#
# Package stage
#
FROM jdk-21
COPY --from=build /app/target/getyourway-0.0.1-SNAPSHOT.jar /usr/local/lib/demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]


