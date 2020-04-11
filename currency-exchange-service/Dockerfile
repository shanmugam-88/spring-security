FROM openjdk:8-jdk-alpine
WORKDIR /opt/app
EXPOSE 8000
ADD target/*.jar exchange-rate.jar
ENTRYPOINT ["java","-jar", "exchange-rate.jar"]
