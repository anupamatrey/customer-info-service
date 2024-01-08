package com.anupam.service;

import com.anupam.config.AWSConfiguration;
import com.anupam.model.Message;
import com.anupam.model.response.Address;
import com.anupam.model.response.Preferences;
import com.anupam.model.response.Response;
import com.anupam.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;

import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerService {
    private final static Logger LOG = LoggerFactory.getLogger(CustomerService.class);

    @Value("${DYNAMO_DB_TABLE_NAME}")
    private String tableName;
    @Autowired
    AWSConfiguration awsConfiguration;

    public com.anupam.model.response.Response getCustomerById(String Id) {
        LOG.info("CONTACT_CHANGE_EVENT: Start getting an object from DynamoDB for the customerId: {} ", Id);
        com.anupam.model.response.Response response = new Response();
        // Create the filter expression and attribute values
        String filterExpression = "customer_id = :customer_id";
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":customer_id", AttributeValue.builder().s(Id).build());
        // Create a QueryRequest object
        QueryRequest queryRequest = QueryRequest.builder()
                .tableName(tableName)
                .keyConditionExpression(filterExpression)
                .expressionAttributeValues(expressionAttributeValues)
                .build();
        try(DynamoDbClient dbClient = awsConfiguration.createDBClient()){
            QueryResponse queryResponse = dbClient.query(queryRequest);
            int itemCount = queryResponse.count();
            if(itemCount>=1)
            {
                //response = buildCustomerResponse(queryResponse);
                Utility messageConvertor = new Utility();
                for (Map<String, AttributeValue> item : queryResponse.items()) {
                    if(item.containsKey("json_data")){
                        return buildCustomerResponse(item);
                    }
                }
            }

        }
        return response;
    }

    private com.anupam.model.response.Response buildCustomerResponse(Map<String, AttributeValue> item) {
        Utility messageConvertor = new Utility();
        com.anupam.model.response.Response customer = new com.anupam.model.response.Response();
        customer.setPhone_number(item.containsKey("phone") ? item.get("phone").s() : null);
        customer.setCustomer_id(item.containsKey("customer_id") ? item.get("customer_id").s() : null);

        if (item.containsKey("json_data")) {
            Map<String, AttributeValue> jsonData = item.get("json_data").m();

            customer.setCustomer_id(jsonData.get("customer_id").s());
            customer.setFirst_name(jsonData.get("first_name").s());
            customer.setLast_name(jsonData.get("last_name").s());
            customer.setEmail(jsonData.get("email").s());
            customer.setPhone_number(jsonData.get("phone_number").s());

            customer.setRegistration_date(jsonData.get("registration_date").s());
            customer.setLast_purchase_date(jsonData.get("last_purchase_date").s());
            customer.setMembership_status(jsonData.get("membership_status").s());
            customer.setTotal_purchases(Integer.parseInt(jsonData.get("total_purchases").n()));
            customer.setAddress(mapToAddress(jsonData.get("address").m()));
            customer.setPreferences(mapToPreferences(jsonData.get("preferences").m()));

        }
        return customer;
    }
    private static Address mapToAddress(Map<String, AttributeValue> addressMap) {
        Address address = new Address();

        address.setState(addressMap.get("state").s());
        address.setCity(addressMap.get("city").s());
        address.setStreet(addressMap.get("street").s());
        address.setZip_code(addressMap.get("zip_code").s());

        return address;
    }
    private static Preferences mapToPreferences(Map<String, AttributeValue> preferencesMap) {
        Preferences preferences = new Preferences();
        preferences.setPreferred_color(preferencesMap.get("preferred_color").s());
        //preferences.setPreferred_category(preferencesMap.get("preferred_category ").s());
        return preferences;
    }
}
