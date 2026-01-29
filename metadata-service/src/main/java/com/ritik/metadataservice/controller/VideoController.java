package com.ritik.metadataservice.controller;

import com.ritik.metadataservice.dtos.CreateVideoRequest;
import com.ritik.metadataservice.dtos.UpdateVideoStatusRequest;
import com.ritik.metadataservice.dtos.VideoResponse;
import com.ritik.metadataservice.services.VideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @PostMapping
    public VideoResponse createVideo(@Valid @RequestBody CreateVideoRequest request) {
        return videoService.createVideo(request);
    }

    @GetMapping("/{videoId}")
    public VideoResponse getVideoById(@PathVariable String videoId) {
        return videoService.getVideoById(videoId);
    }

    @GetMapping
    public Page<VideoResponse> listLatestVideos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return videoService.listByLatest(page, size);
    }

    @GetMapping("/uploader/{uploaderId}")
    public Page<VideoResponse> listVideosByUploader(
            @PathVariable String uploaderId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return videoService.listByUploader(uploaderId, page, size);
    }

    @PatchMapping("/{videoId}/status")
    public VideoResponse updateStatus(
            @PathVariable String videoId,
            @Valid @RequestBody UpdateVideoStatusRequest req
    ) {
        return videoService.updateStatus(videoId, req.getStatus());
    }

}
