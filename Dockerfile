FROM openjdk:17-oracle
ENV AWS_ACCESS_KEY_ID = $AWS_ACCESS_KEY_ID
ENV AWS_SECRET_ACCESS_KEY = $AWS_SECRET_ACCESS_KEY
WORKDIR /app
COPY build/libs/customer-info-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]