package dev.project1backendspring.controller;

import dev.project1backendspring.model.Anime;
import dev.project1backendspring.model.RepositoryException;
import dev.project1backendspring.model.User;
import dev.project1backendspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/")
public class UserController {
    @Autowired
    private final UserService userService;

    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping("/shows/adduser")
    @Transactional
    public ResponseEntity<String> newUser(@RequestBody User newUser){
        try{
            userService.addUser(newUser);
            return ResponseEntity.ok().body("User added");
        }
        catch(RepositoryException exception){
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

    @GetMapping("/shows/user/{id}")
    @Transactional
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        try{
            return ResponseEntity.ok().body(userService.getUserByID(id));
        }
        catch(RepositoryException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PostMapping("/username")
    @Transactional
    public ResponseEntity<?> getUserByUsername(@RequestBody String userName){
        try{
            return ResponseEntity.ok().body(userService.checkUsernameExistence(userName));
        }
        catch(RepositoryException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }


    @GetMapping("/shows/all-users")
    @Transactional
    public ResponseEntity<?> allUsers(){
        try{
            System.out.println("Good Controller");
            return ResponseEntity.ok().body(userService.getAllUsers());
        }
        catch (Exception exception){
            System.out.println("Bad Controller");
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }


    @PutMapping("/shows/update-user/{id}")
    @Transactional
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User updatedUser){
        try{
            userService.updateUser(id, updatedUser);
            return ResponseEntity.ok().body("User updated");
        }
        catch(RepositoryException exception){
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

    @DeleteMapping("/shows/remove-user/{id}")
    @Transactional
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        try{
            userService.deleteUser(id);
            return ResponseEntity.ok().body("User deleted");
        }
        catch(RepositoryException exception){
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }
}
