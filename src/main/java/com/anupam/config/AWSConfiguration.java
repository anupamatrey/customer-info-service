package com.anupam.config;

import com.jayway.jsonpath.JsonPath;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
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

    @Value("${MY_KEY1}")
    private String apiKeyValue1;

    @Value("${key2}")
    private String apiKeyValue2;

//    private static String getSecret(String key) {
//
//        String secretName = "anupam/secret/";
//        Region region = Region.of("us-east-1");
//
//        // Create a Secrets Manager client
//        SecretsManagerClient client = SecretsManagerClient.builder()
//                .region(region)
//                .build();
//
//        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
//                .secretId(secretName)
//                .build();
//
//        GetSecretValueResponse getSecretValueResponse;
//
//        try {
//            getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
//        } catch (Exception e) {
//            throw e;
//        }
//        String secretValue = getSecretValueResponse.secretString();
//        return JsonPath.read(secretValue,"$."+ key);
//    }
    public DynamoDbClient createDBClient() {


        /**
         * Create the AWS credentials provider with BasicAWSCredentials
         */
        AwsCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(AwsBasicCredentials.create(apiKeyValue1,
                apiKeyValue2));

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
