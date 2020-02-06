FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD
MAINTAINER Mahnaz Ebrahimi
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn package clean install
FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/dashboard-application-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java", "-jar", "dashboard-application-0.0.1-SNAPSHOT.jar"]