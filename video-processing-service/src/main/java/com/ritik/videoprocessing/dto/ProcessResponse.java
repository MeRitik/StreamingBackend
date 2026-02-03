package com.ritik.videoprocessing.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProcessResponse {
    private String videoId;
    private String status;
    private String processedKey;
}
