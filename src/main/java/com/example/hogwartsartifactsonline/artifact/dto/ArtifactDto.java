package com.example.hogwartsartifactsonline.artifact.dto;

import com.example.hogwartsartifactsonline.wizard.dto.WizardDto;
import jakarta.validation.constraints.NotEmpty;

public record ArtifactDto(
        String id,
        @NotEmpty(message = "Name is required.")
        String name,
        @NotEmpty(message = "Description is required.")
        String description,
        @NotEmpty(message = "Image URL is required.")
        String imageUrl,
        WizardDto owner
) {
}
