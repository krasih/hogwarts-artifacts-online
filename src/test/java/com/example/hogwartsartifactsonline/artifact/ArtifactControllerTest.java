package com.example.hogwartsartifactsonline.artifact;

import com.example.hogwartsartifactsonline.artifact.dto.ArtifactDto;
import com.example.hogwartsartifactsonline.system.StatusCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Artifact -Controller-")
class ArtifactControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ArtifactService artifactService;

    @Autowired
    ObjectMapper objectMapper;

    List<Artifact> artifacts;

    @BeforeEach
    void setUp() {
        this.artifacts = new ArrayList<>();

        Artifact a1 = new Artifact()
            .setId("1250808601744904191")
            .setName("Deluminator")
            .setDescription("A Deluminator is a device invented by Albus Dumbledore that resembles a cigarette lighter. It is used to remove or absorb (as well as return) the light from any light source to provide cover to the user.")
            .setImageUrl("ImageUrl");
        this.artifacts.add(a1);

        Artifact a2 = new Artifact()
            .setId("1250808601744904192")
            .setName("Invisibility Cloak")
            .setDescription("An invisibility cloak is used to make the wearer invisible.")
            .setImageUrl("ImageUrl");
        this.artifacts.add(a2);

        Artifact a3 = new Artifact()
            .setId("1250808601744904193")
            .setName("Elder Wand")
            .setDescription("The Elder Wand, known throughout history as the Deathstick or the Wand of Destiny, is an extremely powerful wand made of elder wood with a core of Thestral tail hair.")
            .setImageUrl("ImageUrl");
        this.artifacts.add(a3);

        Artifact a4 = new Artifact()
            .setId("1250808601744904194")
            .setName("The Marauder's Map")
            .setDescription("A magical map of Hogwarts created by Remus Lupin, Peter Pettigrew, Sirius Black, and James Potter while they were students at Hogwarts.")
            .setImageUrl("ImageUrl");
        this.artifacts.add(a4);

        Artifact a5 = new Artifact()
            .setId("1250808601744904195")
            .setName("The Sword Of Gryffindor")
            .setDescription("A goblin-made sword adorned with large rubies on the pommel. It was once owned by Godric Gryffindor, one of the medieval founders of Hogwarts.")
            .setImageUrl("ImageUrl");
        this.artifacts.add(a5);

        Artifact a6 = new Artifact()
            .setId("1250808601744904196")
            .setName("Resurrection Stone")
            .setDescription("The Resurrection Stone allows the holder to bring back deceased loved ones, in a semi-physical form, and communicate with them.")
            .setImageUrl("ImageUrl");
        this.artifacts.add(a6);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Find Artifact by ID - [Success]")
    void findArtifactById_Success() throws Exception {

        // Given
        given(this.artifactService.findById("1250808601744904191"))
                .willReturn(this.artifacts.get(0));

        // When and Then
        this.mockMvc
                .perform(get("/api/v1/artifacts/1250808601744904191").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.id").value("1250808601744904191"))
                .andExpect(jsonPath("$.data.name").value("Deluminator"));
    }

    @Test
    @DisplayName("Find Artifact by ID - [Not Found]")
    void findArtifactById_NotFound() throws Exception {

        // Given
        given(this.artifactService.findById("1250808601744904191"))
                .willThrow(new ArtifactNotFoundException("1250808601744904191"));

        // When and Then
        this.mockMvc
                .perform(get("/api/v1/artifacts/1250808601744904191").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find artifact with Id 1250808601744904191 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @DisplayName("Find All Artifacts - [Success]")
    void findAllArtifacts_Success() throws Exception {

        // Given
        given(this.artifactService.findAll())
                .willReturn(this.artifacts);

        // When and Then
        this.mockMvc.perform(get("/api/v1/artifacts").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All Success"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(this.artifacts.size())))
                .andExpect(jsonPath("$.data[0].id").value("1250808601744904191"))
                .andExpect(jsonPath("$.data[0].name").value("Deluminator"))
                .andExpect(jsonPath("$.data[1].id").value("1250808601744904192"))
                .andExpect(jsonPath("$.data[1].name").value("Invisibility Cloak"));
    }

    @Test
    @DisplayName("Add Artifact - [Success]")
    void addArtifact_Success() throws Exception {

        // Given
        ArtifactDto newArtifact =
                new ArtifactDto(null, "Artifact", "Description", "ImageURL", null);

        String json = this.objectMapper.writeValueAsString(newArtifact);

        Artifact savedArtifact = new Artifact()
                .setId("1250808601744904197")
                .setName("Artifact")
                .setDescription("Description")
                .setImageUrl("ImageURL");

        given(this.artifactService.save(Mockito.any(Artifact.class)))
                .willReturn(savedArtifact);

        // When and Then
        this.mockMvc.perform(post("/api/v1/artifacts").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.name").value(savedArtifact.getName()))
                .andExpect(jsonPath("$.data.description").value(savedArtifact.getDescription()))
                .andExpect(jsonPath("$.data.imageUrl").value(savedArtifact.getImageUrl()));
    }

    @Test
    @DisplayName("Update Artifact - [Success]")
    void updateArtifact_Success() throws Exception {

        // Given
        ArtifactDto artifactDto =
                new ArtifactDto("1250808601744904192", "Updated Artifact", "Updated Description", "Updated ImageURL", null);

        String json = this.objectMapper.writeValueAsString(artifactDto);

        Artifact updatedArtifact = new Artifact()
                .setId("1250808601744904192")
                .setName("Updated Artifact")
                .setDescription("Updated Description")
                .setImageUrl("Updated ImageURL");

        given(this.artifactService.update(eq("1250808601744904192"), Mockito.any(Artifact.class)))
                .willReturn(updatedArtifact);

        // When and Then
        this.mockMvc.perform(put("/api/v1/artifacts/1250808601744904192").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Update Success"))
                .andExpect(jsonPath("$.data.id").value(updatedArtifact.getId()))
                .andExpect(jsonPath("$.data.name").value(updatedArtifact.getName()))
                .andExpect(jsonPath("$.data.description").value(updatedArtifact.getDescription()))
                .andExpect(jsonPath("$.data.imageUrl").value(updatedArtifact.getImageUrl()));
    }

    @Test
    @DisplayName("Update Artifact - [Error With No Existent Id]")
    void updateArtifact_ErrorWithNoExistentId() throws Exception {

        // Given
        ArtifactDto artifactDto =
                new ArtifactDto("1250808601744904192", "Updated Artifact", "Updated Description", "Updated ImageURL", null);

        String json = this.objectMapper.writeValueAsString(artifactDto);


        given(this.artifactService.update(eq("1250808601744904192"), Mockito.any(Artifact.class)))
                .willThrow(new ArtifactNotFoundException("1250808601744904192"));

        // When and Then
        this.mockMvc.perform(put("/api/v1/artifacts/1250808601744904192").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find artifact with Id 1250808601744904192 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @DisplayName("Delete Artifact - [Success]")
    void deleteArtifact_Success() throws Exception {

        // Given
        doNothing().when(artifactService).delete("1250808601744904191");

        // When and Then
        this.mockMvc.perform(delete("/api/v1/artifacts/1250808601744904191").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Delete Success"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @DisplayName("Delete Artifact - [Error With No Existent Id]")
    void deleteArtifact_ErrorWithNoExistentId() throws Exception {

        // Given
        doThrow(new ArtifactNotFoundException("1250808601744904191"))
                .when(artifactService).delete("1250808601744904191");

        // When and Then
        this.mockMvc.perform(delete("/api/v1/artifacts/1250808601744904191").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find artifact with Id 1250808601744904191 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}