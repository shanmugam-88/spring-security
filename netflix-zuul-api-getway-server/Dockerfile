FROM openjdk:8-jdk-alpine
WORKDIR /opt/app
EXPOSE 8765
ADD target/*.jar getway-server.jar
ENTRYPOINT ["java","-jar", "getway-server.jar"]
