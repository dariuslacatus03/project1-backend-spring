package dev.project1backendspring.model;

import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId") // Foreign key column
    private User user;

    public Anime(String animeName, String cover, Integer nrOfEpisodes, String genre, String description)
    {
        this.animeName = animeName;
        this.cover = cover;
        this.nrOfEpisodes = nrOfEpisodes;
        this.genre = genre;
        this.description = description;
    }

    public Anime(String animeName, String cover, Integer nrOfEpisodes, String genre, String description, User user)
    {
        this.animeName = animeName;
        this.cover = cover;
        this.nrOfEpisodes = nrOfEpisodes;
        this.genre = genre;
        this.description = description;
        this.user = user;
    }
}
