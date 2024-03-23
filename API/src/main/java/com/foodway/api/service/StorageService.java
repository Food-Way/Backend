package com.foodway.api.service;

import com.foodway.api.record.RequestS3PresignedUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

@Service
public class StorageService {
    @Autowired
    private S3Client s3Client;

    public String generatePresignedUrlWithTags(RequestS3PresignedUrl request) {
        try {
            S3Presigner presigner = S3Presigner.builder()
                    .region(Region.of("us-east-1"))
                    .build();

            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(request.bucketName())
                    .key(request.objectKey())
                    .build();

            PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(p -> p.signatureDuration(Duration.ofMinutes(10))
                    .putObjectRequest(objectRequest));

            URI urlWithTags = new URI(presignedRequest.url().toString() + "?tagging=" + request.tagKey() + "=" + request.tagValue());

            return urlWithTags.toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
}