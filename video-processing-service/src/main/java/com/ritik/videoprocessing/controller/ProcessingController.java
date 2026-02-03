package com.ritik.videoprocessing.controller;

import com.ritik.videoprocessing.dto.ProcessResponse;
import com.ritik.videoprocessing.service.VideoProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/process")
@RequiredArgsConstructor
public class ProcessingController {

    private final VideoProcessingService processingService;

    @PostMapping("/{videoId}")
    public ProcessResponse process(@PathVariable String videoId) throws Exception {
        return processingService.process(videoId);
    }
}
