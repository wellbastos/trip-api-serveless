package br.com.fiap.repository;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class DynamoDbManager {
    private static DynamoDBMapper mapper;

    static {
        AmazonDynamoDB ddb;
        final String endpoint = System.getenv("ENDPOINT_OVERRIDE");

        if (endpoint != null && !endpoint.isEmpty()) {
            EndpointConfiguration endpointConfiguration = new EndpointConfiguration(endpoint, "us-east-1");
            ddb = AmazonDynamoDBClientBuilder
                    .standard()
                    .withEndpointConfiguration(endpointConfiguration).build();
        } else {
            ddb = AmazonDynamoDBClientBuilder.defaultClient();
        }

        mapper = new DynamoDBMapper(ddb);
    }

    public static DynamoDBMapper mapper() {
        return DynamoDbManager.mapper;
    }
}
