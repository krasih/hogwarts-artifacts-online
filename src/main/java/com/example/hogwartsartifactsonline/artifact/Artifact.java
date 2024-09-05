package com.example.hogwartsartifactsonline.artifact;

import com.example.hogwartsartifactsonline.wizard.Wizard;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "artifacts")
public class Artifact implements Serializable {

    @Id
    private String id;
    private String name;
    private String description;
    private String imageUrl;

    @ManyToOne
    private Wizard owner;

    public Artifact() {}

    public String getId() {
        return id;
    }

    public Artifact setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Artifact setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Artifact setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Artifact setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Wizard getOwner() {
        return owner;
    }

    public Artifact setOwner(Wizard owner) {
        this.owner = owner;
        return this;
    }
}
