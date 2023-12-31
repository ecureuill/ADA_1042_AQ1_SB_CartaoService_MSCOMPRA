# Use official maven/Java 8 image as the base image
FROM maven:3.8.4-openjdk-17-slim as build

# Set the current working directory inside the docker image
WORKDIR /app

# Copy maven executable to the image
COPY mvnw .
COPY .mvn .mvn

# Copy the pom.xml file
COPY pom.xml .

# Build all the dependencies in preparation to go offline.
# This is a separate step so the dependencies will be cached unless changes to pom.xml are made.
RUN mvn dependency:go-offline -B

# Copy the project source
COPY src src

# Package the application
RUN mvn package -DskipTests

# The final stage to create an executable image
FROM openjdk:17-jdk-slim

# Make port 8080 available to the world outside this container
EXPOSE 8085

# Add the application's jar to the container
COPY --from=build /app/target/*.jar app.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]