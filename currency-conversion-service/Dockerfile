FROM openjdk:8-jdk-alpine
WORKDIR /opt/app
EXPOSE 8100
ADD target/*.jar exchange-conversion.jar
ENTRYPOINT ["java","-jar", "exchange-conversion.jar"]
