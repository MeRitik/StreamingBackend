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
        String url = baseUrl + "/v1/api/videos/" + videoId + "/status";

        // TODO: Create endpoint like PATCH /v1/api/videos/{id}/processed
        // to store processedKey or url
        restClient.patch()
                .uri(url)
                .body(Map.of("status", "READY"))
                .retrieve()
                .toBodilessEntity();
    }
}
