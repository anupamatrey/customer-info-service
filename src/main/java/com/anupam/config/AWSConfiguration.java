package com.anupam.config;


import com.anupam.controller.CustomerController;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import java.util.Map;


@Configuration
@Slf4j
public class AWSConfiguration {
    private final static Logger LOG = LoggerFactory.getLogger(AWSConfiguration.class);
    @Value("${aws.region}")
    private String region;

    public DynamoDbClient createDBClient() {
        Map<String,String> env = System.getenv();
        env.forEach((k,v)-> System.out.println(k+" : "+v));

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
