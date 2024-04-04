package dev.project1backendspring;


import com.fasterxml.jackson.databind.ObjectMapper;
import dev.project1backendspring.controller.AnimeController;
import dev.project1backendspring.model.Anime;
import dev.project1backendspring.service.AnimeService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;



@ExtendWith(SpringExtension.class)
@WebMvcTest(AnimeController.class)
@AutoConfigureMockMvc(addFilters = false)

public class AnimeControllerTest {
    @MockBean
    AnimeService animeService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAnimeEndpoint_GET_Works() throws Exception {
        Anime newAnime = new Anime(1L, "Dragon Ball Daima", "none", 1, "Action", "Description");

        when(animeService.getAllAnimes()).thenReturn(List.of(newAnime, newAnime));

        when(animeService.getAnimeByID(any(Long.class))).thenReturn(newAnime);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/shows"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].animeName").value("Dragon Ball Daima"));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/shows/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.animeName").value("Dragon Ball Daima"));
    }

    @Test
    public void testAnimeEndpoint_DELETE_Works() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/shows/remove/5")
                        .contentType(MediaType.APPLICATION_JSON)) // Set content type to JSON or any appropriate type
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8"))
                .andExpect(content().string("Anime deleted"));
        verify(animeService).deleteAnime(any(Long.class));
    }

    @Test
    public void testAnimeEndpoint_POST_Works() throws Exception {
        Anime newAnime = new Anime(1L, "Dragon Ball Daima", "none", 1, "Action", "Description");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(newAnime);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/shows/add")
                        .contentType(MediaType.APPLICATION_JSON) // Set content type to JSON
                        .content(requestBody)) // Set request body
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8"))
                .andExpect(content().string("Anime added"));
    }

    @Test
    public void testAnimeEndpoint_PUT_Works() throws Exception {

        Anime updatedAnime = new Anime(1L, "Dragon Ball Daima", "none", 1, "Action", "Description");

        // Convert the updated tank object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(updatedAnime);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/shows/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8"))
                .andExpect(content().string("Anime updated"));
        verify(animeService).updateAnime(any(Long.class), any(Anime.class));

    }
}