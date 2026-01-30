package com.ritik.uploadservice.controller;

import com.ritik.uploadservice.client.MetadataClient;
import com.ritik.uploadservice.dto.UploadCompleteResponse;
import com.ritik.uploadservice.service.UploadCompleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/uploads/videos")
@RequiredArgsConstructor
public class UploadCompleteController {
    private final UploadCompleteService uploadCompleteService;
    private final MetadataClient metadataClient;

    @Value("${app.storage.bucket}")
    private String bucket;

    @PostMapping("/{videoId}/complete")
    public UploadCompleteResponse complete(@PathVariable String videoId) {
        uploadCompleteService.verifyUploaded(videoId);
        metadataClient.markProcessing(videoId);

        return UploadCompleteResponse.builder()
                .videoId(videoId)
                .bucket(bucket)
                .objectKey(uploadCompleteService.getRawKey(videoId))
                .message("Upload verified. Status modified to PROCESSING.")
                .build();
    }
}
