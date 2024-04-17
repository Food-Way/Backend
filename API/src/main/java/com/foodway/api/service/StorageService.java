package com.foodway.api.service;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.Tag;
import software.amazon.awssdk.services.s3.model.Tagging;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.amazonaws.services.s3.model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@PropertySource("classpath:s3.properties")
public class StorageService {
    @Value("${bucketName}")
    private String bucketName;
    private final AmazonS3 s3;

    public StorageService(AmazonS3 s3) {
        this.s3 = s3;
    }

    public String saveFile(MultipartFile file, String path) {
        String originalNameFile = file.getOriginalFilename();
        String fullPath = path + "/" + originalNameFile;
        int count = 0;
        int maxTries = 3;
        while (true) {
            try {
                File multPartFile = convertMultPartToFile(file);
                PutObjectResult putObjectResult = s3.putObject(bucketName, fullPath, multPartFile);
                return putObjectResult.getContentMd5();
            } catch (IOException e) {
                if (++count == maxTries) throw new RuntimeException(e);
            }
        }
    }

    private File convertMultPartToFile(MultipartFile file) throws IOException {
        File convertFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convertFile);
        fos.write(file.getBytes());
        fos.close();
        return convertFile;
    }
}