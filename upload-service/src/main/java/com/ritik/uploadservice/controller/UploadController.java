package com.ritik.uploadservice.controller;

import com.ritik.uploadservice.dto.PresignUploadRequest;
import com.ritik.uploadservice.dto.PresignUploadResponse;
import com.ritik.uploadservice.service.UploadPresignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/uploads/videos")
@RequiredArgsConstructor
public class UploadController {

    private final UploadPresignService presignService;

    @PostMapping("/{videoId}/presign")
    public PresignUploadResponse presign(
            @PathVariable String videoId,
            @Valid @RequestBody PresignUploadRequest request
    ) {
        return PresignUploadResponse.builder()
                .videoId(videoId)
                .uploadUrl(presignService.presignPutRawVideo(videoId, request.getContentType()))
                .build();
    }
}
