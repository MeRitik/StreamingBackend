package com.ritik.uploadservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UploadCompleteResponse {
    private String videoId;
    private String bucket;
    private String objectKey;
    private String message;
}
