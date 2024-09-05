package com.example.hogwartsartifactsonline.artifact;

import com.example.hogwartsartifactsonline.artifact.utils.IdWorker;
import com.example.hogwartsartifactsonline.wizard.Wizard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Artifact -Service-")
class ArtifactServiceTest {

    @Mock
    ArtifactRepository artifactRepository;

    @Mock
    IdWorker idWorker;

    @InjectMocks
    ArtifactService artifactService;

    List<Artifact> artifacts;

    @BeforeEach
    void setUp() {

        Artifact a1 = new Artifact()
            .setId("1250808601744904191")
            .setName("Deluminator")
            .setDescription("A Deluminator is a device invented by Albus Dumbledore that resembles a cigarette lighter. It is used to remove or absorb (as well as return) the light from any light source to provide cover to the user.")
            .setImageUrl("imageUrl");

        Artifact a2 = new Artifact()
            .setId("1250808601744904192")
            .setName("Invisibility Cloak")
            .setDescription("An invisibility cloak is used to make the wearer invisible.")
            .setImageUrl("imageUrl");

        this.artifacts = new ArrayList<>();
        this.artifacts.add(a1);
        this.artifacts.add(a2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Find by ID - [Success]")
    void findById_Success() {

        // Given > Arrange inputs and targets. Define the behavior of Mock object artifactRepository
        Artifact artifact = new Artifact()
                .setId("1250808601744904192")
                .setName("Invisibility Cloak")
                .setDescription("An invisibility cloak is used to make the wearer invisible.")
                .setImageUrl("ImageUrl");

        Wizard wizard = new Wizard()
                .setId(2)
                .setName("Harry Potter");

        artifact.setOwner(wizard);

        given(artifactRepository.findById("1250808601744904192"))
                .willReturn(Optional.of(artifact));                     // Defines the behavior of the mocked object


        // When > Act on the target behavior. When steps should cover the method to be tested
        Artifact returnedArtifact = artifactService.findById("1250808601744904192");

        // Then > Assert expected outcomes
        assertThat(returnedArtifact.getId()).isEqualTo(artifact.getId());
        assertThat(returnedArtifact.getName()).isEqualTo(artifact.getName());
        assertThat(returnedArtifact.getDescription()).isEqualTo(artifact.getDescription());
        assertThat(returnedArtifact.getImageUrl()).isEqualTo(artifact.getImageUrl());
        verify(artifactRepository, times(1)).findById("1250808601744904192");

    }

    @Test
    @DisplayName("Find by ID - [Not Found]")
    void findById_NotFound() {

        // Given
        given(artifactRepository.findById(Mockito.any(String.class)))
                .willReturn(Optional.empty());

        // When
        Throwable thrown = catchThrowable(() -> {
            Artifact returnedArtifact = artifactService.findById("1250808601744904192");
        });

        // Then
        assertThat(thrown)
                .isInstanceOf(ArtifactNotFoundException.class)
                .hasMessage("Could not find artifact with Id 1250808601744904192 :(");
        verify(artifactRepository, times(1)).findById("1250808601744904192");
    }

    @Test
    @DisplayName("Find All - [Success]")
    void findAll_Success() {

        // Given
        given(artifactRepository.findAll())
                .willReturn(this.artifacts);

        // When
        List<Artifact> actualArtifacts = artifactService.findAll();

        // Then
        assertThat(actualArtifacts.size()).isEqualTo(this.artifacts.size());
        verify(artifactRepository, times(1)).findAll();

    }

    @Test
    @DisplayName("Save Artifact - [Success]")
    void save_Success() {

        // Given
        Artifact newArtifact = new Artifact()
                .setName("New Artifact...")
                .setDescription("New Description...")
                .setImageUrl("New Image URL...");

        given(idWorker.nextId()).willReturn(123456L);
        given(artifactRepository.save(newArtifact)).willReturn(newArtifact);

        // When
        Artifact savedArtifact = artifactService.save(newArtifact);

        // Then
        assertThat(savedArtifact.getId()).isEqualTo("123456");
        assertThat(savedArtifact.getName()).isEqualTo("New Artifact...");
        assertThat(savedArtifact.getDescription()).isEqualTo("New Description...");
        assertThat(savedArtifact.getImageUrl()).isEqualTo("New Image URL...");
        verify(artifactRepository, times(1)).save(newArtifact);
    }

    @Test
    @DisplayName("Update Artifact - [Success]")
    void update_Success() {

        // Given
        Artifact oldArtifact = new Artifact()
                .setId("1250808601744904192")
                .setName("Invisibility Cloak")
                .setDescription("An invisibility cloak is used to make the wearer invisible.")
                .setImageUrl("imageUrl");

        Artifact update = new Artifact()
                .setId("1250808601744904192")
                .setName("Invisibility Cloak")
                .setDescription("New description")
                .setImageUrl("imageUrl");

        given(artifactRepository.findById("1250808601744904192")).willReturn(Optional.of(oldArtifact));
        given(artifactRepository.save(oldArtifact)).willReturn(oldArtifact);

        // When
        Artifact updatedArtifact = artifactService.update("1250808601744904192", update);

        // Then
        assertThat(updatedArtifact.getId()).isEqualTo(update.getId());
        assertThat(updatedArtifact.getDescription()).isEqualTo(update.getDescription());
        verify(artifactRepository, times(1)).findById("1250808601744904192");
        verify(artifactRepository, times(1)).save(oldArtifact);
    }

    @Test
    @DisplayName("Update Artifact - [Not Found]")
    void update_NotFound() {

        // Given
        Artifact update = new Artifact()
                .setName("Invisibility Cloak")
                .setDescription("New description")
                .setImageUrl("imageUrl");

        given(artifactRepository.findById("1250808601744904192")).willReturn(Optional.empty());

        // When
        assertThrows(ArtifactNotFoundException.class, () -> {
            artifactService.update("1250808601744904192", update);
        });

        // Then
        verify(artifactRepository, times(1)).findById("1250808601744904192");
    }

    @Test
    @DisplayName("Delete Artifact - [Success]")
    void delete_Success() {

        // Given
        Artifact artifact = new Artifact()
                .setId("123456")
                .setName("Delete Artifact")
                .setDescription("Description - this artifact to be deleted")
                .setImageUrl("imageUrl");

        given(artifactRepository.findById("123456")).willReturn(Optional.of(artifact));
        doNothing().when(artifactRepository).deleteById("123456");

        // When
        artifactService.delete("123456");

        // Then
        verify(artifactRepository, times(1)).deleteById("123456");
    }

    @Test
    @DisplayName("Delete Artifact - [Not Found]")
    void delete_NotFound() {

        // Given
        given(artifactRepository.findById("123456")).willReturn(Optional.empty());

        // When
        assertThrows(ArtifactNotFoundException.class, () -> {
            artifactService.delete("123456");
        });

        // Then
        verify(artifactRepository, times(1)).findById("123456");
    }

}