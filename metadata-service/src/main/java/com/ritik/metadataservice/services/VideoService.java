package com.ritik.metadataservice.services;

import com.ritik.metadataservice.dtos.CreateVideoRequest;
import com.ritik.metadataservice.dtos.VideoResponse;
import org.springframework.data.domain.Page;

public interface VideoService {
    VideoResponse createVideo(CreateVideoRequest request);
    VideoResponse getVideoById(String videoId);
    Page<VideoResponse> listByLatest(int page, int size);
    Page<VideoResponse> listByUploader(String uploaderId, int page, int size);
}
