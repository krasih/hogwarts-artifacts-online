package com.example.hogwartsartifactsonline.artifact.converter;

import com.example.hogwartsartifactsonline.artifact.Artifact;
import com.example.hogwartsartifactsonline.artifact.dto.ArtifactDto;
import com.example.hogwartsartifactsonline.wizard.converter.WizardToWizardDtoConverter;
import com.example.hogwartsartifactsonline.wizard.dto.WizardDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArtifactDtoToArtifactConverter implements Converter<ArtifactDto, Artifact> {

    @Override
    public Artifact convert(ArtifactDto dto) {

        return new Artifact()
                .setId(dto.id())
                .setName(dto.name())
                .setDescription(dto.description())
                .setImageUrl(dto.imageUrl());
    }
}
