//package com.foodway.api.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
//import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
//import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
//import software.amazon.awssdk.regions.Region;
//import software.amazon.awssdk.services.s3.S3Client;
//
//@Configuration
//public class AmazonS3Config {
//    @Bean
//    public S3Client s3Client() {
//        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
//                System.getenv("AWS_ACCESS_KEY_ID"),
//                System.getenv("AWS_SECRET_ACCESS_KEY")
//        );
//        return S3Client.builder()
//                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
//                .region(Region.of("us-east-1"))
//                .build();
//    }
//}