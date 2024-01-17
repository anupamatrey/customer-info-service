FROM openjdk:17-oracle
# Set the environment variables required for your application
ENV SECRET_1=${secrets.AWS_ACCESS_KEY_ID}
ENV SECRET_2=${secrets.AWS_SECRET_ACCESS_KEY}
WORKDIR /app
COPY build/libs/customer-info-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]