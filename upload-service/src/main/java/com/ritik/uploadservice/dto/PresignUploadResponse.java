package com.ritik.uploadservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PresignUploadResponse {
    private String videoId;
    private String uploadUrl;
}
