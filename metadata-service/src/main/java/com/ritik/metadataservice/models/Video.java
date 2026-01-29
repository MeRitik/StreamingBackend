package com.ritik.metadataservice.models;

import com.ritik.metadataservice.models.enums.VideoStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "videos")
public class Video {
    @Id
    private String id;

    @Indexed
    private String uploaderId;

    private String title;
    private String description;
    private VideoStatus status;

    private Instant createdAt;
    private Instant updatedAt;

    private String rawUrl;
    private String processedUrl;
    private String thumbnailUrl;

    private Long durationSeconds;
    private Long viewCount;
}
