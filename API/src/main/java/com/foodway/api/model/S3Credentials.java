package com.foodway.api.model;
public class S3Credentials {
    private String accessKey;
    private String secret;
    private String region;
    private String bucketName;
    private String sessionToken;

    public S3Credentials() {
    }

    public S3Credentials(String accessKey, String secret, String region, String bucketName, String sessionToken) {
        this.accessKey = accessKey;
        this.secret = secret;
        this.region = region;
        this.bucketName = bucketName;
        this.sessionToken = sessionToken;
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

