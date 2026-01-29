package com.ritik.metadataservice.dtos;

import com.ritik.metadataservice.models.enums.VideoStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateVideoStatusRequest {

    @NotNull
    private VideoStatus status;
}
