package dev.project1backendspring.controller;

import dev.project1backendspring.model.Anime;
import dev.project1backendspring.model.RepositoryException;
import dev.project1backendspring.service.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@CrossOrigin
@RequestMapping("/")
public class AnimeController {
    private final AnimeService animeService;

    @Autowired
    public AnimeController(AnimeService animeService){
        this.animeService = animeService;
    }


    @PostMapping("/shows/add")
    ResponseEntity<String> newTank(@RequestBody Anime newAnime){
        try{
            animeService.addAnime(newAnime);
            return ResponseEntity.ok().body("Anime added");
        }
        catch(RepositoryException exception){
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }


    @GetMapping("/shows/{id}")
    ResponseEntity<?> getAnimeById(@PathVariable Long id){
        try{
            return ResponseEntity.ok().body(animeService.getAnimeByID(id));
        }
        catch(RepositoryException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @GetMapping("/shows")
    ResponseEntity<?> allShows(){
        try{
            List<AnimeDTO> animeDTOList = animeService.getAllAnimes()
                    .stream()
                    .map(this::mapAnimeToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(animeDTOList);
        }
        catch (Exception exception){
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }
    private record AnimeDTO(Long id, String animeName, int nrOfEpisodes) {}
    private AnimeDTO mapAnimeToDTO(Anime anime) {
        return new AnimeDTO(anime.getId(), anime.getAnimeName(), anime.getNrOfEpisodes());
    }

    @PutMapping("/shows/update/{id}")
    ResponseEntity<String> updateTank(@PathVariable Long id, @RequestBody Anime updatedAnime){
        try{
            animeService.updateAnime(id, updatedAnime);
            return ResponseEntity.ok().body("Anime updated");
        }
        catch(RepositoryException exception){
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

    @DeleteMapping("/shows/remove/{id}")
    ResponseEntity<String> deleteTank(@PathVariable Long id){
        try{
            animeService.deleteAnime(id);
            return ResponseEntity.ok().body("Anime deleted");
        }
        catch(RepositoryException exception){
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }
}