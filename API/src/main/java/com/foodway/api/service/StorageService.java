//package com.foodway.api.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import software.amazon.awssdk.core.sync.RequestBody;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.services.s3.model.PutObjectRequest;
//import software.amazon.awssdk.services.s3.model.Tag;
//import software.amazon.awssdk.services.s3.model.Tagging;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class StorageService {
//    @Autowired
//    private S3Client s3Client;
//
//    public ResponseEntity<String> uploadFileToS3(MultipartFile file, String bucketName, String objectKey, String tagKey, String tagValue) {
//        try {
//            Map<String, String> metadata = new HashMap<>();
//            metadata.put("Content-Type", file.getContentType());
//
//            Tagging tagging = Tagging.builder()
//                    .tagSet(Tag.builder().key(tagKey).value(tagValue).build())
//                    .build();
//
//            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
//                    .bucket(bucketName)
//                    .key(objectKey)
//                    .metadata(metadata)
//                    .tagging(tagging)
//                    .build();
//
//            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
//
//            String fileUrl = s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(objectKey)).toString();
//            return ResponseEntity.ok(fileUrl);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.internalServerError().body("Erro ao fazer upload do arquivo");
//        }
//    }
//}