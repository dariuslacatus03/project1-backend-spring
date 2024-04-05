package dev.project1backendspring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;


@Entity
@Getter @Setter @AllArgsConstructor @RequiredArgsConstructor @NoArgsConstructor @ToString
public class Anime {
//    id: 4,
//    name: 'Dragon Ball Z',
//    cover: 'none',
//    nrOfEpisodes: 291,
//    genre: 'Action',
//    description: 'This is the description of Dragon Ball Z'
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
