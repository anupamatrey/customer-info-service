FROM openjdk:17-oracle
ENV key1 = $key1
ENV key2 = $key2
ENV secret_manager = aws-secretsmanager:anupam/secret/
WORKDIR /app
COPY build/libs/customer-info-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]