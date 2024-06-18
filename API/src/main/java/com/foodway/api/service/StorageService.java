package com.foodway.api.service;

import com.amazonaws.services.s3.AmazonS3;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.foodway.api.handler.exceptions.UserNotFoundException;
import com.foodway.api.model.Customer;
import com.foodway.api.model.Enums.ETypeUser;
import com.foodway.api.model.Establishment;

import com.foodway.api.model.User;
import com.foodway.api.repository.CustomerRepository;
import com.foodway.api.repository.EstablishmentRepository;
import com.foodway.api.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import com.amazonaws.services.s3.model.*;

import java.time.LocalDateTime;
import java.util.*;

@Service
@PropertySource("classpath:s3.properties")
public class StorageService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    EstablishmentRepository establishmentRepository;
    @Value("${bucketName}")
    private String bucketName;
    private final AmazonS3 s3;
    public StorageService(AmazonS3 s3) {
        this.s3 = s3;
    }
    public ResponseEntity<String> saveProfileHeaderPhoto(MultipartFile file, UUID idUser, ETypeUser typeUser) {
        String profilePhotoUrl;
        ObjectTagging tagging = new ObjectTagging(Arrays.asList(new Tag("environment", "public")));
        Optional<? extends User> userOptional = findUserByIdAndType(idUser, typeUser);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        User user = userOptional.get();
        String fileName = generateFileName(user.getName(), "profile-header");
        String basePath = user instanceof Customer ? "customer" : "establishment";
        String fullPath = basePath + "/images/" + user.getIdUser() + "/" + fileName;
        try {
            uploadFile(file, fullPath, tagging);
            profilePhotoUrl = "https://foodway.s3.amazonaws.com/" + fullPath;
            user.setProfileHeaderImg(profilePhotoUrl);
            saveUser(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file due to: " + e.getMessage());
        }
        return ResponseEntity.status(200).body(profilePhotoUrl);
    }

    public ResponseEntity<String> saveProfilePhoto(MultipartFile file, UUID idUser, ETypeUser typeUser) {
        String profilePhotoUrl;
        ObjectTagging tagging = new ObjectTagging(Arrays.asList(new Tag("environment", "public")));
        Optional<? extends User> userOptional = findUserByIdAndType(idUser, typeUser);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        User user = userOptional.get();
        String fileName = generateFileName(user.getName(), "profile-photo");
        String basePath = user instanceof Customer ? "customer" : "establishment";
        String fullPath = basePath + "/images/" + user.getIdUser() + "/" + fileName;
        try {
            uploadFile(file, fullPath, tagging);
            profilePhotoUrl = "https://foodway.s3.amazonaws.com/" + fullPath;
            user.setProfilePhoto(profilePhotoUrl);
            saveUser(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file due to: " + e.getMessage());
        }
        return ResponseEntity.status(200).body(profilePhotoUrl);
    }


    public ResponseEntity<String> saveProductImage(MultipartFile file, UUID idUser, ETypeUser typeUser) {
        Long count = productRepository.count();
        String profilePhotoUrl;
        ObjectTagging tagging = new ObjectTagging(Arrays.asList(new Tag("environment", "public")));
        Optional<? extends User> userOptional = findUserByIdAndType(idUser, typeUser);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        User user = userOptional.get();
        String fileName = generateFileName(user.getName(), "profile-photo");
        String basePath = user instanceof Customer ? "customer" : "establishment";
        String fullPath = basePath + "/product/" + user.getIdUser() + "/" + fileName + (count + 1);
        try {
            uploadFile(file, fullPath, tagging);
            profilePhotoUrl = "https://foodway.s3.amazonaws.com/" + fullPath;
            user.setProfilePhoto(profilePhotoUrl);
            saveUser(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file due to: " + e.getMessage());
        }
        return ResponseEntity.status(200).body(profilePhotoUrl);
    }

//    public ResponseEntity<String> saveProductImage(MultipartFile file){
//        String profilePhotoUrl;
//        ObjectTagging tagging = new ObjectTagging(Arrays.asList(new Tag("environment", "public")));
//        LocalDateTime dateTime = LocalDateTime.now();
//
//        String fileName = generateFileName( "produto","product-image");
//        String fullPath = "product" + "/images/" + fileName;
//        try {
//            uploadFile(file, fullPath, tagging);
//            profilePhotoUrl = "https://foodway.s3.amazonaws.com/" + fullPath;
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to upload file due to: " + e.getMessage());
//        }
//        return ResponseEntity.status(200).body(profilePhotoUrl);
//    }
    private Optional<? extends User> findUserByIdAndType(UUID idUser, ETypeUser typeUser) {
        if (typeUser == ETypeUser.CLIENT) {
            return customerRepository.findById(idUser);
        } else if (typeUser == ETypeUser.ESTABLISHMENT) {
            return establishmentRepository.findById(idUser);
        }
        return Optional.empty();
    }

    private void saveUser(User user) {
        if (user instanceof Customer) {
            customerRepository.save((Customer) user);
        } else if (user instanceof Establishment) {
            establishmentRepository.save((Establishment) user);
        }
    }

    private String generateFileName(String name, String prefix) {
        if (name != null) name = name.replaceAll("\\s+", "");
        if (prefix != null) prefix = prefix.replaceAll("\\s+", "");
        if (name.isEmpty()) name = "unknown";
        if (prefix.isEmpty()) prefix = "unknown";
        return name + "_" + prefix;
    }
    private File convertMultPartToFile(MultipartFile file) throws IOException {
        File convertFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convertFile);
        fos.write(file.getBytes());
        fos.close();
        return convertFile;
    }

    private String uploadFile(MultipartFile file, String fullPath,ObjectTagging tagging){
        int count = 0;
        int maxTries = 3;
        while (true) {
            try {
                File multiPartFile = convertMultPartToFile(file);
                PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fullPath, multiPartFile)
                        .withTagging(tagging);
                PutObjectResult putObjectResult = s3.putObject(putObjectRequest);
                return putObjectResult.getContentMd5();
            } catch (IOException e) {
                if (++count == maxTries) throw new RuntimeException(e);
            }
        }
    }
}