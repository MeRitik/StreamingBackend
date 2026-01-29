package com.ritik.metadataservice.repositories;

import com.ritik.metadataservice.models.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository extends MongoRepository<Video, String> {
    Page<Video> findByUploaderIdOrderByCreatedAtDesc(String uploaderId, Pageable pageable);
    Page<Video> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
