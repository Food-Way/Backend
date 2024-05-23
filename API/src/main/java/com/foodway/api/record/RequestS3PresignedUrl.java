package com.foodway.api.record;

public record RequestS3PresignedUrl(
        String bucketName,
        String objectKey,
        String tagKey,
        String tagValue
) {
}
