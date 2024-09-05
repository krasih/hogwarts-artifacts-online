package com.example.hogwartsartifactsonline.artifact.converter;

import com.example.hogwartsartifactsonline.artifact.Artifact;
import com.example.hogwartsartifactsonline.artifact.dto.ArtifactDto;
import com.example.hogwartsartifactsonline.wizard.converter.WizardToWizardDtoConverter;
import com.example.hogwartsartifactsonline.wizard.dto.WizardDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArtifactToArtifactDtoConverter implements Converter<Artifact, ArtifactDto> {

    private final WizardToWizardDtoConverter wizardToWizardDtoConverter;

    public ArtifactToArtifactDtoConverter(WizardToWizardDtoConverter wizardToWizardDtoConverter) {
        this.wizardToWizardDtoConverter = wizardToWizardDtoConverter;
    }

    @Override
    public ArtifactDto convert(Artifact artifact) {

        return new ArtifactDto(
                artifact.getId(),
                artifact.getName(),
                artifact.getDescription(),
                artifact.getImageUrl(),
                this.getOwner(artifact));
    }

    private WizardDto getOwner(Artifact artifact) {

        return artifact.getOwner() != null
                ? this.wizardToWizardDtoConverter.convert(artifact.getOwner())
                : null;
    }
}
