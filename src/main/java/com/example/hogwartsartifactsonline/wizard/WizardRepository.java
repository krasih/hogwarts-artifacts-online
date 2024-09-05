package com.example.hogwartsartifactsonline.wizard;

import com.example.hogwartsartifactsonline.artifact.Artifact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WizardRepository extends JpaRepository<Wizard, Integer> {
}
