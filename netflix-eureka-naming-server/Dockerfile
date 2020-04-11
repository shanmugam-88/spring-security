FROM openjdk:8-jdk-alpine
WORKDIR /opt/app
EXPOSE 8761
ADD target/*.jar naming-server.jar
ENTRYPOINT ["java","-jar", "naming-server.jar"]
