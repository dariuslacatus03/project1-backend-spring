package dev.project1backendspring;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.project1backendspring.controller.AnimeController;
import dev.project1backendspring.controller.UserController;
import dev.project1backendspring.model.Anime;
import dev.project1backendspring.model.User;
import dev.project1backendspring.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
    @MockBean
    UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUserEndpoint_GET_Works() throws Exception {
        User newUser = new User("user1");
        when(userService.getAllUsers()).thenReturn(List.of(newUser, newUser));

        when(userService.getUserByID(any(Long.class))).thenReturn(newUser);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/shows/all-users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].userName").value("user1"));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/shows/user/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userName").value("user1"));
    }

    @Test
    public void testUserEndpoint_DELETE_Works() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/shows/remove-user/5")
                        .contentType(MediaType.APPLICATION_JSON)) // Set content type to JSON or any appropriate type
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8"))
                .andExpect(content().string("User deleted"));
        verify(userService).deleteUser(any(Long.class));
    }

    @Test
    public void testUserEndpoint_POST_Works() throws Exception {
        User newUser = new User("user1");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(newUser);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/shows/adduser")
                        .contentType(MediaType.APPLICATION_JSON) // Set content type to JSON
                        .content(requestBody)) // Set request body
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8"))
                .andExpect(content().string("User added"));
    }

    @Test
    public void testAnimeEndpoint_PUT_Works() throws Exception {

        User updatedUser = new User("user1");

        // Convert the updated tank object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(updatedUser);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/shows/update-user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8"))
                .andExpect(content().string("User updated"));
        verify(userService).updateUser(any(Long.class), any(User.class));

    }
}
