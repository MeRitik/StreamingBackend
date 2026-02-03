package com.ritik.videoprocessing.service;

import com.ritik.videoprocessing.dto.ProcessResponse;

public interface VideoProcessingService {
    ProcessResponse process(String videoId) throws Exception;
}
