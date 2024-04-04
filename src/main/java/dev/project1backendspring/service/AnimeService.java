package dev.project1backendspring.service;

import dev.project1backendspring.model.Anime;
import dev.project1backendspring.model.RepositoryException;
import dev.project1backendspring.repository.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimeService {
    AnimeRepository animeRepository;

    @Autowired
    public AnimeService(AnimeRepository animeRepo)
    {
        this.animeRepository = animeRepo;
        Anime anime1 = new Anime("Dragon Ball", "none", 126, "Action", "This is the description of Dragon Ball");
        Anime anime2 = new Anime("Hunter x Hunter", "none", 126, "Adventure", "This is the description of Hunter x Hunter");
        Anime anime3 = new Anime("Sailor Moon", "none", 250, "Shojo", "This is the description of Sailor Moon");
        Anime anime4 = new Anime("Dragon Ball Z", "none", 291, "Action", "This is the description of Dragon Ball Z");
        Anime anime5 = new Anime("One Piece", "none", 1098, "Adventure", "This is the description of One Piece");
        Anime anime6 = new Anime("Jojo's Bizzare Adventure", "none", 190, "Adventure", "This is the description of Jojo's Bizzare Adventure");
        Anime anime7 = new Anime("Baki", "none", 40, "Action", "This is the description of Baki");
        Anime anime8 = new Anime("One Punch Man", "none", 24, "Comedy", "This is the description of One Punch Man");
        Anime anime9 = new Anime("Spy x Family", "none", 37, "Comedy", "This is the description of Spy x Family");
        Anime anime10 = new Anime("Dragon Ball Super", "none", 131, "Action", "This is the description of Dragon Ball Super");
        Anime anime11 = new Anime("Dragon Ball GT", "none", 64, "Action", "This is the description of Dragon Ball GT");
        Anime anime12 = new Anime("Jujutsu Kaisen", "none", 70, "Action", "This is the description of Jujutsu Kaisen");
        this.animeRepository.save(anime1);
        this.animeRepository.save(anime2);
        this.animeRepository.save(anime3);
        this.animeRepository.save(anime4);
        this.animeRepository.save(anime5);
        this.animeRepository.save(anime6);
        this.animeRepository.save(anime7);
        this.animeRepository.save(anime8);
        this.animeRepository.save(anime9);
        this.animeRepository.save(anime10);
        this.animeRepository.save(anime11);
        this.animeRepository.save(anime12);
    }

    public void addAnime(Anime animeToAdd) throws RepositoryException
    {
        try
        {
            this.animeRepository.save(animeToAdd);
        }
        catch (Exception exception)
        {
            throw new RepositoryException("Couldn't save anime");
        }
    }

    public Anime getAnimeByID(Long id) throws RepositoryException
    {
        try
        {
            return this.animeRepository.findById(id).get();
        }
        catch(Exception exception)
        {
            throw new RepositoryException("Anime with given ID not found");
        }
    }
    public List<Anime> getAllAnimes() throws RepositoryException
    {
        try
        {
            return this.animeRepository.findAll();
        }
        catch (Exception exception)
        {
            throw new RepositoryException("Couldn't get all animes");
        }

    }

    public void updateAnime(Long id, Anime updatedAnime) throws RepositoryException
    {
        try
        {
            Optional<Anime> animeToUpdate = animeRepository.findById(id);
            if (animeToUpdate.isEmpty())
            {
                throw new RepositoryException("Anime you want to update does not exist");
            }
            animeRepository.save(updatedAnime);
        }
        catch (Exception exception)
        {
            throw new RepositoryException("Couldn't update anime");
        }
    }

    public void deleteAnime(Long id) throws RepositoryException
    {
        try
        {
            animeRepository.deleteById(id);
        }
        catch(Exception exception)
        {
            throw new RepositoryException("Couldn't delete anime");
        }
    }

}
