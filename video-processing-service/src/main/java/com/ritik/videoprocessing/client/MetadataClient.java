package com.ritik.videoprocessing.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class MetadataClient {

    private final RestClient restClient;
    
    @Value("${metadata.base-url}")
    private String baseUrl;

    public void markReady(String videoId, String processedKey) {
        String url = baseUrl + "/api/v1/videos/" + videoId + "/status";

        // TODO: Create endpoint like PATCH /v1/api/videos/{id}/processed
        // to store processedKey or url
        restClient.patch()
                .uri(url)
                .body(Map.of("status", "READY"))
                .retrieve()
                .toBodilessEntity();
    }

    public void markFailed(String videoId) {
        restClient.patch()
                .uri(baseUrl + "/api/v1/videos/" + videoId + "/status")
                .body(Map.of("status", "FAILED"))
                .retrieve()
                .toBodilessEntity();
    }
}
