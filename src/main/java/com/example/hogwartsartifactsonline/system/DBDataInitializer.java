package com.example.hogwartsartifactsonline.system;

import com.example.hogwartsartifactsonline.artifact.Artifact;
import com.example.hogwartsartifactsonline.artifact.ArtifactRepository;
import com.example.hogwartsartifactsonline.wizard.Wizard;
import com.example.hogwartsartifactsonline.wizard.WizardRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBDataInitializer implements CommandLineRunner {

    private final ArtifactRepository artifactRepository;
    private final WizardRepository wizardRepository;

    public DBDataInitializer(ArtifactRepository artifactRepository, WizardRepository wizardRepository) {
        this.artifactRepository = artifactRepository;
        this.wizardRepository = wizardRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Artifact a1 = new Artifact()
            .setId("1250808601744904191")
            .setName("Deluminator")
            .setDescription("A Deluminator is a device invented by Albus Dumbledore that resembles a cigarette lighter. It is used to remove or absorb (as well as return) the light from any light source to provide cover to the user.")
            .setImageUrl("https://hogwartsartifactsonline.blob.core.windows.net/artifact-image-container/deluminator.jpg");

        Artifact a2 = new Artifact()
            .setId("1250808601744904192")
            .setName("Invisibility Cloak")
            .setDescription("An invisibility cloak is used to make the wearer invisible.")
            .setImageUrl("https://hogwartsartifactsonline.blob.core.windows.net/artifact-image-container/invisibility-cloak.jpg");

        Artifact a3 = new Artifact()
            .setId("1250808601744904193")
            .setName("Elder Wand")
            .setDescription("The Elder Wand, known throughout history as the Deathstick or the Wand of Destiny, is an extremely powerful wand made of elder wood with a core of Thestral tail hair.")
            .setImageUrl("https://hogwartsartifactsonline.blob.core.windows.net/artifact-image-container/elder-wand.jpg");

        Artifact a4 = new Artifact()
            .setId("1250808601744904194")
            .setName("The Marauder's Map")
            .setDescription("A magical map of Hogwarts created by Remus Lupin, Peter Pettigrew, Sirius Black, and James Potter while they were students at Hogwarts.")
            .setImageUrl("https://hogwartsartifactsonline.blob.core.windows.net/artifact-image-container/marauders-map.jpg");

        Artifact a5 = new Artifact()
            .setId("1250808601744904195")
            .setName("The Sword Of Gryffindor")
            .setDescription("A goblin-made sword adorned with large rubies on the pommel. It was once owned by Godric Gryffindor, one of the medieval founders of Hogwarts.")
            .setImageUrl("https://hogwartsartifactsonline.blob.core.windows.net/artifact-image-container/sword-of-gryffindor.jpg");

        Artifact a6 = new Artifact()
            .setId("1250808601744904196")
            .setName("Resurrection Stone")
            .setDescription("The Resurrection Stone allows the holder to bring back deceased loved ones, in a semi-physical form, and communicate with them.")
            .setImageUrl("https://hogwartsartifactsonline.blob.core.windows.net/artifact-image-container/resurrection-stone.jpg");

        Wizard w1 = new Wizard()
            .setId(1)
            .setName("Albus Dumbledore");
        w1.addArtifact(a1);
        w1.addArtifact(a3);

        Wizard w2 = new Wizard()
            .setId(2)
            .setName("Harry Potter");
        w2.addArtifact(a2);
        w2.addArtifact(a4);

        Wizard w3 = new Wizard()
            .setId(3)
            .setName("Neville Longbottom");
        w3.addArtifact(a5);

        wizardRepository.save(w1);
        wizardRepository.save(w2);
        wizardRepository.save(w3);

        artifactRepository.save(a6);

        // TODO: Create some users
    }

}
