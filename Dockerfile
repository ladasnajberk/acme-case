FROM openjdk:8-jdk-alpine
MAINTAINER Lada.Snajberk
VOLUME /tmp
EXPOSE 8080
ADD build/libs/acme-0.0.1-SNAPSHOT.jar acme.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/acme.jar"]