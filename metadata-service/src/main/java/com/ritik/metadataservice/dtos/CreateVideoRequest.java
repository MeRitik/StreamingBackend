package com.ritik.metadataservice.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateVideoRequest {

    @NotBlank
    private String uploaderId;

    @NotBlank
    @Size(min = 3)
    private String title;

    @NotBlank
    private String description;
}

