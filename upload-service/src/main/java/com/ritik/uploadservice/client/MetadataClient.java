package com.ritik.uploadservice.client;

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

    public void markProcessing(String videoId) {
        String urlToMarkVideoProcessing = baseUrl + "/api/v1/videos/" + videoId + "/status";

        restClient.patch()
                .uri(urlToMarkVideoProcessing)
                .body(Map.of("status", "PROCESSING"))
                .retrieve()
                .toBodilessEntity();
    }
}
