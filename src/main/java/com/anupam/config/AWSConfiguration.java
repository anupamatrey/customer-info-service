package com.anupam.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;


@Configuration
@Slf4j
public class AWSConfiguration {
    @Value("${aws.region}")
    private String region;

    public DynamoDbClient createDBClient() {

        DynamoDbClient client = DynamoDbClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .build();

        /**
         * Create the AWS credentials provider with BasicAWSCredentials
         */
        AwsCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(AwsBasicCredentials.create(System.getenv("SECRET_1"),
                System.getenv("SECRET_2")));

        /**
         * Adding this code only for local testing for timeout use cases for AWS DynamoDB
         * Set up a custom SdkHttpClient with a connection timeout (in milliseconds)
         **/

        /**
         * int connectionTimeoutMillis = 1; // 1 ms
         * SdkHttpClient httpClient = ApacheHttpClient.builder().connectionTimeout(Duration.ofMillis(connectionTimeoutMillis)).build();
         */

        /**
         * Create DynamoDB client with the AWS credentials
         */
        return DynamoDbClient.builder()
                .region(Region.of(region))
                /**
                 * Un-Comment below line , if want to test time-out use case
                 */
                //.httpClient(httpClient)
                .credentialsProvider(credentialsProvider)
                .build();
    }
}
