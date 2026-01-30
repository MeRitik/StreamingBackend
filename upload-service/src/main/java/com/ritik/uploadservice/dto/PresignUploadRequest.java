package com.ritik.uploadservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PresignUploadRequest {
    @NotBlank
    private String contentType;
}
