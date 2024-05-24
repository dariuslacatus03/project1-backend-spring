package dev.project1backendspring.controller;

import dev.project1backendspring.model.Anime;
import dev.project1backendspring.model.RepositoryException;
import dev.project1backendspring.model.User;
import dev.project1backendspring.service.AnimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@CrossOrigin
@RequestMapping("/")
@EnableScheduling
@RequiredArgsConstructor
public class AnimeController {

    private final AnimeService animeService;
    private SimpMessagingTemplate messagingTemplate;



//    @MessageMapping("/getShows")
//    @Scheduled(fixedDelay = 5000)
//    @SendTo("/topic/status")
//    public void sendAnime()
//    {
//        try
//        {
//            messagingTemplate.convertAndSend("/topic/status", animeService.getAllAnimes());
//        }
//        catch (RepositoryException exception)
//        {
//            // Log the exception or handle it accordingly
//            exception.printStackTrace();
//        }
//    }

    @PostMapping("/shows/add")
    @Transactional
    public ResponseEntity<String> newAnime(@RequestBody Anime newAnime){
        try{
            animeService.addAnime(newAnime);
            return ResponseEntity.ok().body("Anime added");
        }
        catch(RepositoryException exception){
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

//    private record AnimeDTO(Long id, String animeName, int nrOfEpisodes, String genre, String description, String userName) {}
//    private AnimeDTO mapAnimeToDTO(Anime anime) {
//        return new AnimeDTO(anime.getId(), anime.getAnimeName(), anime.getNrOfEpisodes(), anime.getGenre(), anime.getDescription(), anime.getUser().getUserName());
//    }

    @GetMapping("/shows/{id}")
    @Transactional
    public ResponseEntity<?> getAnimeById(@PathVariable Long id){
        try{
            return ResponseEntity.ok().body(animeService.getAnimeByID(id));
        }
        catch(RepositoryException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    private record AnimeDTO2(Long id, String animeName, int nrOfEpisodes, String genre, User user) {}
    private AnimeDTO2 mapAnimeToDTO2(Anime anime) {
        return new AnimeDTO2(anime.getId(), anime.getAnimeName(), anime.getNrOfEpisodes(), anime.getGenre(), anime.getUser());
    }
    @GetMapping("/shows")
    @Transactional
    public ResponseEntity<?> allShows(){
        try{
            System.out.println("Good Controller");
            List<AnimeDTO2> animeDTOList = animeService.getAllAnimes()
                    .stream()
                    .map(this::mapAnimeToDTO2)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(animeDTOList);
        }
        catch (Exception exception){
            System.out.println("Bad Controller");
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }


    @PutMapping("/shows/update/{id}")
    @Transactional
    public ResponseEntity<String> updateShow(@PathVariable Long id, @RequestBody Anime updatedAnime){
        try{
            animeService.updateAnime(id, updatedAnime);
            return ResponseEntity.ok().body("Anime updated");
        }
        catch(RepositoryException exception){
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

    @DeleteMapping("/shows/remove/{id}")
    @Transactional
    public ResponseEntity<String> deleteShow(@PathVariable Long id){
        try{
            animeService.deleteAnime(id);
            return ResponseEntity.ok().body("Anime deleted");
        }
        catch(RepositoryException exception){
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }
}