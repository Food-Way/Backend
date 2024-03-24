package com.foodway.api.controller;

import com.foodway.api.record.RequestS3;
import com.foodway.api.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
@Tag(name = "S3")
public class S3Controller {

    @Autowired
    private StorageService s3Service;

    @PostMapping("/upload")
    @Operation(summary = "Upload a file to S3", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("bucketName") String bucketName,
                                                   @RequestParam("objectKey") String objectKey,
                                                   @RequestParam("tagKey") String tagKey,
                                                   @RequestParam("tagValue") String tagValue) {
        return s3Service.uploadFileToS3(file, bucketName, objectKey, tagKey, tagValue);
    }
}