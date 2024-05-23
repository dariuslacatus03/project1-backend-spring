package dev.project1backendspring.service;

import dev.project1backendspring.model.Anime;
import dev.project1backendspring.model.RepositoryException;
import dev.project1backendspring.model.User;
import dev.project1backendspring.repository.AnimeRepository;
import dev.project1backendspring.repository.UserRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.github.javafaker.Faker;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AnimeService {

    @Autowired
    AnimeRepository animeRepository;

    @Autowired
    UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public AnimeService(AnimeRepository animeRepo, EntityManager entityMan, UserRepository userRepo)
    {
        this.animeRepository = animeRepo;
        this.entityManager = entityMan;
        this.userRepository = userRepo;
        //vvv COMMENT OUT WHEN RUNNING TESTS
        this.generateFakeAnimes(48);
    }

    public void addAnime(Anime animeToAdd) throws RepositoryException
    {
        try
        {
            if (!userRepository.existsById(animeToAdd.getUser().getId())) {
                throw new RepositoryException("User does not exist");
            }

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
            System.out.println("Good Service");
            return this.animeRepository.findAll();
        }
        catch (Exception exception)
        {
            System.out.println("Bad Service");
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

//    @Transactional
//    public List<User> getAllUsers() {
//        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
//    }
    public void generateFakeAnimes(int numberOfAnimes)
    {
        List<User> USERS = userRepository.findAll();
        if (USERS.isEmpty()) {
            return;
        }
        for (User user : USERS)
        {
            System.out.println(user.getUserName());
        }
        String[] ANIME_NAMES = {
                "Naruto", "One Piece", "Dragon Ball Z", "Attack on Titan", "My Hero Academia",
                "Death Note", "Fullmetal Alchemist", "Tokyo Ghoul", "Sword Art Online", "Demon Slayer",
                "One Punch Man", "Hunter x Hunter", "Fairy Tail", "Bleach", "Black Clover",
                "Neon Genesis Evangelion", "Cowboy Bebop", "Code Geass", "JoJo's Bizarre Adventure",
                "Fruits Basket", "Haikyu!!", "Re:Zero", "The Promised Neverland", "Steins;Gate",
                "Your Lie in April", "Attack on Titan", "Dragon Ball", "Boku no Hero Academia",
                "Kimetsu no Yaiba", "Shingeki no Kyojin"
        };

        String[] ANIME_GENRES = {
                "Action", "Adventure", "Comedy", "Drama", "Fantasy",
                "Horror", "Mystery", "Psychological", "Romance", "Science Fiction"
        };

        Faker faker = new Faker();

        for (int i = 0; i < numberOfAnimes; i++)
        {
            Anime anime = new Anime();
            anime.setAnimeName(ANIME_NAMES[faker.random().nextInt(ANIME_NAMES.length)]);
            anime.setCover("none");
            anime.setNrOfEpisodes(faker.number().numberBetween(1, 1000));
            anime.setGenre(ANIME_GENRES[faker.random().nextInt(ANIME_GENRES.length)]);
            anime.setDescription("This is the description of " + ANIME_NAMES[faker.random().nextInt(ANIME_NAMES.length)]);
            anime.setUser(USERS.get(i % USERS.size()));
            animeRepository.save(anime);
        }
    }

//    @Scheduled(fixedDelay = 5000)
//    @Async
//    public void generateNewAnime()
//    {
//        generateFakeAnimes(1);
//    }


}
