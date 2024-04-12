package dev.project1backendspring.controller;

import dev.project1backendspring.model.Anime;
import dev.project1backendspring.model.RepositoryException;
import dev.project1backendspring.service.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/")
@EnableScheduling
public class AnimeController {

    @Autowired
    private final AnimeService animeService;

    @Autowired
    public AnimeController(AnimeService animeService){
        this.animeService = animeService;
    }

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/getShows")
    @Scheduled(fixedDelay = 5000)
    @SendTo("/topic/status")
    public void sendAnime()
    {
        try
        {
            messagingTemplate.convertAndSend("/topic/status", animeService.getAllAnimes());
        }
        catch (RepositoryException exception)
        {
            // Log the exception or handle it accordingly
            exception.printStackTrace();
        }
    }

    @PostMapping("/shows/add")
    ResponseEntity<String> newAnime(@RequestBody Anime newAnime){
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
            System.out.println("Good Controller");
            List<AnimeDTO> animeDTOList = animeService.getAllAnimes()
                    .stream()
                    .map(this::mapAnimeToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(animeDTOList);
        }
        catch (Exception exception){
            System.out.println("Bad Controller");
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }
    private record AnimeDTO(Long id, String animeName, int nrOfEpisodes, String genre) {}
    private AnimeDTO mapAnimeToDTO(Anime anime) {
        return new AnimeDTO(anime.getId(), anime.getAnimeName(), anime.getNrOfEpisodes(), anime.getGenre());
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