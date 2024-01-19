FROM openjdk:17-oracle
# Set the environment variables required for your application
ENV SECRET_1=${SECRET_1}
ENV SECRET_2=${SECRET_2}
WORKDIR /app
COPY build/libs/customer-info-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]