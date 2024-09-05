package com.example.hogwartsartifactsonline.artifact;

import com.example.hogwartsartifactsonline.artifact.utils.IdWorker;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ArtifactService {

    private final ArtifactRepository artifactRepository;
    private final IdWorker idWorker;

    public ArtifactService(ArtifactRepository artifactRepository, IdWorker idWorker) {
        this.artifactRepository = artifactRepository;
        this.idWorker = idWorker;
    }

    public Artifact findById(String artifactId) {

        return artifactRepository.findById(artifactId)
                .orElseThrow(() -> new ArtifactNotFoundException(artifactId));
    }

    public List<Artifact> findAll() {

        return this.artifactRepository.findAll();
    }

    public Artifact save(Artifact newArtifact) {

        newArtifact.setId(idWorker.nextId() +"");
        return this.artifactRepository.save(newArtifact);
    }

    public Artifact update(String artifactId, Artifact update) {

        return this.artifactRepository.findById(artifactId)
                        .map(artifact -> {
                            artifact.setName(update.getName())
                                    .setDescription(update.getDescription())
                                    .setImageUrl(update.getImageUrl());

                            return this.artifactRepository.save(artifact);
                        })
                        .orElseThrow(() -> new ArtifactNotFoundException(artifactId));
    }

    public void delete(String artifactId) {

        this.artifactRepository.findById(artifactId)
                .orElseThrow(() -> new ArtifactNotFoundException(artifactId));

        this.artifactRepository.deleteById(artifactId);
    }
}
