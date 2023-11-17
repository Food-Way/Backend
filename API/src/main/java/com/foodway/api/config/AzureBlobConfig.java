package com.foodway.api.config;


import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureBlobConfig {


    private String connectionString = "DefaultEndpointsProtocol=https;AccountName=foodway;AccountKey=3iKt59oT0C3dNy0DtRf5KIBDov3C5aqZ6L8CWHPhFvyHUAHbpVXK+TErO0DSSGCGsn5oJyGNeGzX+AStjKQEiQ==;EndpointSuffix=core.windows.net";


    private String containerName ="user-images";

    @Bean
    public BlobServiceClient clobServiceClient() {

        BlobServiceClient blobServiceClient =
                new BlobServiceClientBuilder()
                        .connectionString(connectionString)
                        .buildClient();

        return blobServiceClient;

    }

    @Bean
    public BlobContainerClient blobContainerClient() {

        BlobContainerClient blobContainerClient =
                clobServiceClient()
                        .getBlobContainerClient(containerName);

        return blobContainerClient;

    }
}