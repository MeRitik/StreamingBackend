package com.ritik.metadataservice.services.impl;

import com.ritik.metadataservice.dtos.CreateVideoRequest;
import com.ritik.metadataservice.dtos.VideoResponse;
import com.ritik.metadataservice.exceptions.ResourceNotFoundException;
import com.ritik.metadataservice.models.Video;
import com.ritik.metadataservice.models.enums.VideoStatus;
import com.ritik.metadataservice.repositories.VideoRepository;
import com.ritik.metadataservice.services.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IVideoService implements VideoService {

    private final VideoRepository videoRepository;

    public VideoResponse createVideo(CreateVideoRequest request) {
        // TODO: DON'T FIX THE PREFIX OF VIDEO
        String videoId = "vid_" + UUID.randomUUID();
        Instant now = Instant.now();

        Video video = Video.builder()
                .id(videoId)
                .uploaderId(request.getUploaderId())
                .title(request.getTitle())
                .description(request.getDescription())
                .status(VideoStatus.UPLOADING)
                .createdAt(now)
                .updatedAt(now)
                .viewCount(0L)
                .build();

        videoRepository.save(video);
        return VideoResponse.from(video);
    }

    public VideoResponse getVideoById(String videoId) {
        return videoRepository.findById(videoId).map(VideoResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("video", "videoId", videoId));
    }

    public Page<VideoResponse> listByLatest(int page, int size) {
        return videoRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size))
                .map(VideoResponse::from);
    }

    public Page<VideoResponse> listByUploader(String uploaderId, int page, int size) {
        return videoRepository.findByUploaderIdOrderByCreatedAtDesc(uploaderId, PageRequest.of(page, size))
                .map(VideoResponse::from);
    }

    public VideoResponse updateStatus(String videoId, VideoStatus status) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResourceNotFoundException("video", "videoId", videoId));

        video.setStatus(status);
        video.setUpdatedAt(Instant.now());
        return VideoResponse.from(videoRepository.save(video));
    }
}
