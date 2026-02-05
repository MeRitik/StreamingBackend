package com.ritik.videoprocessing.service.impl;

import com.ritik.videoprocessing.client.MetadataClient;
import com.ritik.videoprocessing.dto.ProcessResponse;
import com.ritik.videoprocessing.service.VideoProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class IVideoProcessingService implements VideoProcessingService {

    private final S3Client s3;
    private final MetadataClient metadataClient;

    @Value("${app.storage.bucket}")
    private String bucket;

    @Value("${app.storage.raw-prefix}")
    private String rawPrefix;

    @Value("${app.storage.processed-prefix}")
    private String processedPrefix;

    @Value("${app.storage.local-workdir}")
    private String workdir;

    @Override
    public ProcessResponse process(String videoId) throws Exception {
        String rawKey = rawPrefix + "/" + videoId + ".mp4";
        String processedKey = processedPrefix + "/" + videoId + "/720p.mp4";

        Path dir = Path.of(workdir, videoId);

        Path input = dir.resolve("input.mp4");
        Path output = dir.resolve("720p.mp4");

        cleanDirectory(dir);
        Files.createDirectories(dir);

        // Download Raw
        s3.getObject(
                GetObjectRequest.builder().bucket(bucket).key(rawKey).build(),
                ResponseTransformer.toFile(input)
        );

        try {
            // ffmpeg transcode
            runFfmpeg720p(input.toFile(), output.toFile());

            // Upload processed file
            s3.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucket)
                            .key(processedKey)
                            .contentType("video/mp4")
                            .contentLength(Files.size(output))
                            .build(),
                    output
            );

            metadataClient.markReady(videoId, processedKey);
        } catch (Exception e) {
            metadataClient.markFailed(videoId);
            throw e;
        }

        return ProcessResponse.builder()
                .videoId(videoId)
                .status("READY")
                .processedKey(processedKey)
                .build();
    }

    private void runFfmpeg720p(File input, File output) throws Exception {
//        ProcessBuilder pb = new ProcessBuilder(
//                "ffmpeg",
//                "-y",
//                "-i", input.getAbsolutePath(),
//                "-vf", "scale=-2:720",
//                "-c:v", "libx264",
//                "-preset", "veryfast",
//                "-crf", "28",
//                "-c:a", "aac",
//                "-b:a", "128k",
//                output.getAbsolutePath()
//        );

        // TODO: CHANGE IN PROD
        ProcessBuilder pb = new ProcessBuilder(
                "ffmpeg",
                "-y",
                "-t", "10",
                "-i", input.getAbsolutePath(),
                "-vf", "scale=-2:360",
                "-c:v", "libx264",
                "-preset", "ultrafast",
                "-crf", "35",
                "-c:a", "aac",
                "-b:a", "96k",
                output.getAbsolutePath()
        );


        pb.redirectErrorStream(true);
        int code = pb.start().waitFor();

        if (code != 0) {
            throw new RuntimeException("FFMPEG failed with exit code " + code);
        }
    }

    private void cleanDirectory(Path dir) throws IOException {
        if (!Files.exists(dir)) return;

        try (Stream<Path> paths = Files.walk(dir)) {
            for (Path path : paths.sorted(Comparator.reverseOrder()).toList()) {
                Files.deleteIfExists(path);
            }
        }
    }

}
