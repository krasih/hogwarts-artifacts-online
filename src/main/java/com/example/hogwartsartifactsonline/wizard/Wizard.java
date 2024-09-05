package com.example.hogwartsartifactsonline.wizard;

import com.example.hogwartsartifactsonline.artifact.Artifact;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wizards")
public class Wizard implements Serializable {

    @Id
    private Integer id;
    private String name;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "owner")
    private List<Artifact> artifacts = new ArrayList<>();

    public Wizard() {}

    public Integer getId() {
        return id;
    }

    public Wizard setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Wizard setName(String name) {
        this.name = name;
        return this;
    }

    public List<Artifact> getArtifacts() {
        return artifacts;
    }

    public Wizard setArtifacts(List<Artifact> artifacts) {
        this.artifacts = artifacts;
        return this;
    }

    public void addArtifact(Artifact artifact) {

        artifact.setOwner(this);
        this.artifacts.add(artifact);
    }

    public Integer getNumberOfArtifacts() {

        return this.artifacts.size();
    }
}
