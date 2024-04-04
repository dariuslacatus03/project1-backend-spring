package dev.project1backendspring;

import dev.project1backendspring.model.Anime;
import dev.project1backendspring.model.RepositoryException;
import dev.project1backendspring.repository.AnimeRepository;
import dev.project1backendspring.service.AnimeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AnimeServiceTest {
    @Mock
    AnimeRepository animeRepository;

    @InjectMocks
    private AnimeService animeService;

    @Test
    public void testAddAnime() throws RepositoryException {

        Anime newAnime = new Anime(1L, "Dragon Ball Daima", "none", 1, "Action", "Description");

        when(animeRepository.save(any(Anime.class))).thenReturn(newAnime);
        animeService.addAnime(newAnime);
    }

    @Test
    public void testRemoveAnime() throws RepositoryException {
        Anime newAnime = new Anime(1L, "Dragon Ball Daima", "none", 1, "Action", "Description");
        animeService.deleteAnime(newAnime.getId());
        verify(animeRepository).deleteById(newAnime.getId());
    }


    @Test
    public void testGetAnimeById_Found() throws RepositoryException {
        Long animeId = 1L;
        Anime expectedAnime = new Anime(); // Assume Anime is a valid entity class
        when(animeRepository.findById(animeId)).thenReturn(Optional.of(expectedAnime));

        Anime resultAnime = animeService.getAnimeByID(animeId);

        assertThat(resultAnime).isNotNull();
        assertThat(resultAnime).isSameAs(expectedAnime);
    }

    @Test
    public void testGetAnimeById_Not_Found() throws RepositoryException {
        Long animeId = 1L;
        // Assume Anime is a valid entity class
        when(animeRepository.findById(animeId)).thenReturn(Optional.empty());
        assertThatThrownBy(()->animeService.getAnimeByID(animeId)).isInstanceOf(RepositoryException.class).hasMessage("Anime with given ID not found");
    }

    @Test
    public void deleteAnime_Found() throws RepositoryException {
        Long animeId = 1L;

        animeService.deleteAnime(animeId);
        verify(animeRepository).deleteById(animeId);
    }

    @Test void deleteAnime_Not_Found(){
        Long animeId = 1L;

        doThrow(new RuntimeException()).when(animeRepository).deleteById(animeId);
        assertThatThrownBy(()->animeService.deleteAnime(animeId)).isInstanceOf(RepositoryException.class).hasMessage("Couldn't delete anime");

    }

    @Test void updateAnime_Found() throws RepositoryException {
        //Optional<Tank> tankToUpdate = tankRepository.findById(id);
        Anime newAnime = new Anime(1L, "Dragon Ball Daima", "none", 1, "Action", "Description");
        when(animeRepository.findById(newAnime.getId())).thenReturn(Optional.of(newAnime));

        when(animeRepository.save(newAnime)).thenReturn(newAnime);

        animeService.updateAnime(newAnime.getId(), newAnime);
    }


    @Test void getAll_Working() throws RepositoryException {

        Anime newAnime = new Anime(1L, "Dragon Ball Daima", "none", 1, "Action", "Description");
        List<Anime> list = List.of(newAnime, newAnime);

        when(animeRepository.findAll()).thenReturn(list);

        assertThat(animeService.getAllAnimes().get(0).getId()).isSameAs(list.get(0).getId());
        assertThat(animeService.getAllAnimes().get(1).getId()).isSameAs(list.get(1).getId());
    }

    @Test void getAll_Not_Working() throws RepositoryException {

        doThrow(new RuntimeException()).when(animeRepository).findAll();
        assertThatThrownBy(()->animeService.getAllAnimes()).isInstanceOf(RepositoryException.class);
    }

}