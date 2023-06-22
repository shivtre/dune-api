package com.wissen.tresata.web.controller.opensearchControllerTest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wissen.tresata.web.controller.opensearchController.FavoriteController;
import com.wissen.tresata.web.model.opensearchResponse.FavoriteResponse;
import com.wissen.tresata.web.service.opensearchService.FavoriteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Shubham Patel
 *
 * Test cases for FavoriteController
 */

class FavoriteControllerTest {

    private static final String BASE_URL = "/favorites";

    private MockMvc mockMvc;

    @Mock
    private FavoriteService favoriteService;

    @InjectMocks
    private FavoriteController favoriteController;

    @BeforeEach
    public void initMocks(){
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.favoriteController).build();
    }

    @Test
    void getAllFavorites() throws Exception {
        Mockito.when(favoriteService.getAllFavorites()).thenReturn(getAllFavoriteResponse());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/getAllFavorites")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();
        List<FavoriteResponse> response = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {});

        Mockito.verify(favoriteService, Mockito.times(1)).getAllFavorites();

        assertNotNull(response);
        assertEquals("id1", response.get(0).getId());
        assertEquals("path1/path2", response.get(0).getFilepath());
        assertTrue(response.get(0).isActive());
        assertEquals("User1", response.get(0).getUser());
    }

    private List<FavoriteResponse> getAllFavoriteResponse() {
        FavoriteResponse favoriteResponse = new FavoriteResponse();
        favoriteResponse.setId("id1");
        favoriteResponse.setFilepath("path1/path2");
        favoriteResponse.setActive(true);
        favoriteResponse.setUser("User1");

        return List.of(favoriteResponse);
    }

    @Test
    void markFavorite() throws Exception {
        Mockito.when(favoriteService.markFavorite("path1/path2", true)).thenReturn(markFavoriteResponse());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/markFavorite")
                        .param("filePath", "path1/path2")
                        .param("status", "true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Mockito.verify(favoriteService, Mockito.times(1)).markFavorite(ArgumentMatchers.anyString(), ArgumentMatchers.anyBoolean());

        ObjectMapper mapper = new ObjectMapper();
        FavoriteResponse response = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<FavoriteResponse>() {
        });

        assertNotNull(response);
        assertEquals("id1", response.getId());
        assertEquals("path1/path2", response.getFilepath());
        assertTrue(response.isActive());
        assertEquals("User1", response.getUser());
    }

    private FavoriteResponse markFavoriteResponse() {
        FavoriteResponse favoriteResponse = new FavoriteResponse();
        favoriteResponse.setId("id1");
        favoriteResponse.setFilepath("path1/path2");
        favoriteResponse.setActive(true);
        favoriteResponse.setUser("User1");

        return favoriteResponse;
    }
}