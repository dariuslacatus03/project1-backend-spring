package dev.project1backendspring.service;

import dev.project1backendspring.model.Anime;
import dev.project1backendspring.model.RepositoryException;
import dev.project1backendspring.model.User;
import dev.project1backendspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepo)
    {
        this.userRepository = userRepo;
        User user1 = new User("lacatusdarius");
        User user2 = new User("contiumario");
        User user3 = new User("lucaobis");
        this.userRepository.save(user1);
        this.userRepository.save(user2);
        this.userRepository.save(user3);
    }

    public void addUser(User userToAdd) throws RepositoryException
    {
        try
        {
            this.userRepository.save(userToAdd);
        }
        catch (Exception exception)
        {
            throw new RepositoryException("Couldn't save user");
        }
    }

    public User getUserByID(Long id) throws RepositoryException
    {
        try
        {
            return this.userRepository.findById(id).get();
        }
        catch(Exception exception)
        {
            throw new RepositoryException("User with given ID not found");
        }
    }

    public User checkUsernameExistence(String userName) throws RepositoryException
    {
        try
        {
            System.out.println(userName);
            for (User user : userRepository.findAll())
            {
                if (userName.equals(user.getUserName())) {
                    return user;
                }
                System.out.println(user.getUserName());
            }
            return new User();
        }
        catch(Exception exception)
        {
            throw new RepositoryException("User with given ID not found");
        }
    }

    public List<User> getAllUsers() throws RepositoryException
    {
        try
        {
            System.out.println("Good Service User");
            return this.userRepository.findAll();
        }
        catch (Exception exception)
        {
            System.out.println("Bad Service User");
            throw new RepositoryException("Couldn't get all users");
        }
    }

    public void updateUser(Long id, User updatedUser) throws RepositoryException
    {
        try
        {
            Optional<User> userToUpdate = userRepository.findById(id);
            if (userToUpdate.isEmpty())
            {
                throw new RepositoryException("User you want to update does not exist");
            }
            userRepository.save(updatedUser);
        }
        catch (Exception exception)
        {
            throw new RepositoryException("Couldn't update user");
        }
    }
    public void deleteUser(Long id) throws RepositoryException
    {
        try
        {
            userRepository.deleteById(id);
        }
        catch(Exception exception)
        {
            throw new RepositoryException("Couldn't delete user");
        }
    }
}

