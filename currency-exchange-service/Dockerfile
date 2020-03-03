FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8000
ADD target/currency-exchange-service.jar currency-exchange-service.jar
ENTRYPOINT ["sh","-c", "java -jar /currency-exchange-service.jar"]
