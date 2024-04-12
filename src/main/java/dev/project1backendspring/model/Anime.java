package dev.project1backendspring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;


@Entity
@Getter @Setter @AllArgsConstructor @RequiredArgsConstructor @NoArgsConstructor @ToString
public class Anime {
    private @Id @GeneratedValue Long id;
    @NonNull
    private String animeName;

    private String cover;
    @NonNull
    private Integer nrOfEpisodes;
    @NonNull
    private String genre;
    @NonNull
    private String description;

    public Anime(String animeName, String cover, Integer nrOfEpisodes, String genre, String description)
    {
        this.animeName = animeName;
        this.cover = cover;
        this.nrOfEpisodes = nrOfEpisodes;
        this.genre = genre;
        this.description = description;
    }
}
