//package dev.project1backendspring;
//
//import dev.project1backendspring.model.Anime;
//import dev.project1backendspring.model.RepositoryException;
//import dev.project1backendspring.model.User;
//import dev.project1backendspring.repository.AnimeRepository;
//import dev.project1backendspring.repository.UserRepository;
//import dev.project1backendspring.service.AnimeService;
//import dev.project1backendspring.service.UserService;
//import jakarta.persistence.TypedQuery;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import jakarta.persistence.EntityManager;
//
//@ExtendWith(MockitoExtension.class)
//public class UserServiceTest {
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private UserService userService;
//
//    @Test
//    public void testAddUser() throws RepositoryException
//    {
//        User newUser = new User("user1");
//        when(userRepository.save(any(User.class))).thenReturn(newUser);
//        userService.addUser(newUser);
//        verify(userRepository, times(1)).save(newUser);
//    }
//
//    @Test
//    public void testRemoveUser() throws RepositoryException
//    {
//        User newUser = new User("user1");
//        userService.deleteUser(newUser.getId());
//        verify(userRepository).deleteById(newUser.getId());
//    }
//
//    @Test
//    public void testGetUserById_Found() throws RepositoryException {
//        Long userId = 1L;
//        User expectedUser = new User(); // Assume Anime is a valid entity class
//        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));
//
//        User resultUser = userService.getUserByID(userId);
//
//        assertThat(resultUser).isNotNull();
//        assertThat(resultUser).isSameAs(expectedUser);
//    }
//
//    @Test
//    public void testGetUserById_Not_Found() throws RepositoryException {
//        Long userId = 1L;
//        // Assume Anime is a valid entity class
//        when(userRepository.findById(userId)).thenReturn(Optional.empty());
//        assertThatThrownBy(()->userService.getUserByID(userId)).isInstanceOf(RepositoryException.class).hasMessage("User with given ID not found");
//    }
//
//    @Test
//    public void deleteUser_Found() throws RepositoryException {
//        Long userId = 1L;
//
//        userService.deleteUser(userId);
//        verify(userRepository).deleteById(userId);
//    }
//
//    @Test void deleteUser_Not_Found(){
//        Long userId = 1L;
//
//        doThrow(new RuntimeException()).when(userRepository).deleteById(userId);
//        assertThatThrownBy(()->userService.deleteUser(userId)).isInstanceOf(RepositoryException.class).hasMessage("Couldn't delete user");
//
//    }
//
//    @Test void updateUser_Found() throws RepositoryException {
//        User newUser = new User("user1");
//        when(userRepository.findById(newUser.getId())).thenReturn(Optional.of(newUser));
//
//        when(userRepository.save(newUser)).thenReturn(newUser);
//
//        userService.updateUser(newUser.getId(), newUser);
//    }
//
//
//    @Test void getAll_Working() throws RepositoryException {
//
//        User newUser = new User("user1");
//        List<User> list = List.of(newUser, newUser);
//
//        when(userRepository.findAll()).thenReturn(list);
//
//        assertThat(userService.getAllUsers().get(0).getId()).isSameAs(list.get(0).getId());
//        assertThat(userService.getAllUsers().get(1).getId()).isSameAs(list.get(1).getId());
//    }
//
//    @Test void getAll_Not_Working() throws RepositoryException {
//
//        doThrow(new RuntimeException()).when(userRepository).findAll();
//        assertThatThrownBy(()->userService.getAllUsers()).isInstanceOf(RepositoryException.class);
//    }
//}
