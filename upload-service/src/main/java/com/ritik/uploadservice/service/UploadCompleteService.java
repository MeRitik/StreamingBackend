package com.ritik.uploadservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

@Service
@RequiredArgsConstructor
public class UploadCompleteService {

    private final S3Client s3;

    @Value("${app.storage.bucket}")
    private String bucket;

    @Value("${app.storage.raw-prefix}")
    private String rawPrefix;

    public String getRawKey(String videoId) {
        return rawPrefix + "/" + videoId + ".mp4";
    }

    public void verifyUploaded(String videoId) {
        String key = getRawKey(videoId);

        try {
            s3.headObject(HeadObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build());
        } catch (NoSuchKeyException e) {
            throw new RuntimeException("Raw file not found in storage for videoId: " + videoId);
        }
    }
}
