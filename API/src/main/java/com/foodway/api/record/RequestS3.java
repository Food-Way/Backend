package com.foodway.api.record;

import org.springframework.web.multipart.MultipartFile;

public record RequestS3(
        MultipartFile file,
        String bucketName,
        String objectKey,
        String tagKey,
        String tagValue
) {
}
