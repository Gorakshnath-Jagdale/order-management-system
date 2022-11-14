FROM amazoncorretto:11-alpine-jdk
ARG JAR_FILE=target/*.jar
COPY target/docker-order-management-system-0.0.1-SNAPSHOT.jar order-management-system-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/order-management-system-0.0.1-SNAPSHOT.jar"]
