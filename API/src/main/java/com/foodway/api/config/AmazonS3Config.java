package com.foodway.api.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
@Configuration
@Data
@PropertySource("classpath:s3.properties")
public class AmazonS3Config {
    @Value("${accessKey}")
    private String accessKey;
    @Value("${secret}")
    private String secret;
    @Value("${region}")
    private String region;
    @Value("${bucketName}")
    private String bucketName;
    @Value("${sessionToken}")
    private String sessionToken;

    public AmazonS3Config() {
    }

    public AmazonS3Config(String accessKey, String secret, String region, String bucketName, String sessionToken) {
        this.accessKey = accessKey;
        this.secret = secret;
        this.region = region;
        this.bucketName = bucketName;
        this.sessionToken = sessionToken;
    }

    @Bean
    public AmazonS3 s3() {
        AWSCredentials credentials = new BasicSessionCredentials(accessKey, secret, sessionToken);
        return AmazonS3ClientBuilder.standard().withRegion(region).withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}


