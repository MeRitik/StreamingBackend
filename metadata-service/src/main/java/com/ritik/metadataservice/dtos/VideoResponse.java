package com.ritik.metadataservice.dtos;

import com.ritik.metadataservice.models.Video;
import com.ritik.metadataservice.models.enums.VideoStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class VideoResponse {
    private String videoId;
    private String uploaderId;
    private String title;
    private String description;
    private VideoStatus status;
    private Instant createdAt;
    private Instant updatedAt;

    public static VideoResponse from(Video video) {
        return VideoResponse.builder()
                .videoId(video.getId())
                .uploaderId(video.getUploaderId())
                .title(video.getTitle())
                .description(video.getDescription())
                .status(video.getStatus())
                .createdAt(video.getCreatedAt())
                .updatedAt(video.getUpdatedAt())
                .build();
    }
}
