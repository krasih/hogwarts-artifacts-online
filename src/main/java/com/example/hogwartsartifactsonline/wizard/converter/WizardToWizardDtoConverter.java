package com.example.hogwartsartifactsonline.wizard.converter;

import com.example.hogwartsartifactsonline.wizard.Wizard;
import com.example.hogwartsartifactsonline.wizard.dto.WizardDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WizardToWizardDtoConverter implements Converter<Wizard, WizardDto> {

    @Override
    public WizardDto convert(Wizard wizard) {

        return new WizardDto(wizard.getId(), wizard.getName(), wizard.getNumberOfArtifacts());
    }
}
