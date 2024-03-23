package com.foodway.api.controller;
import com.foodway.api.record.RequestS3PresignedUrl;
import com.foodway.api.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/files")
@Tag(name = "S3")
public class S3Controller {

    @Autowired
    private StorageService s3Service;

    @PostMapping("/s3/presigned-url")
    @Operation(summary = "Create a presigned url", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return presigned URL"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public String generatePresignedUrl(@RequestBody @Valid RequestS3PresignedUrl request) {
        return s3Service.generatePresignedUrlWithTags(request);
    }
}