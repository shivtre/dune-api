package com.wissen.tresata.web.service.opensearchService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wissen.tresata.web.client.opensearch.FavoriteClient;
import com.wissen.tresata.web.model.opensearchResponse.*;
import com.wissen.tresata.web.service.opensearchService.impl.FavoriteServiceImpl;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Shubham Patel
 *
 * Test cases for FavoriteService
 */
class FavoriteServiceTest {

    @Mock
    private FavoriteClient favoriteClient;
    @InjectMocks
    private FavoriteServiceImpl favoriteService;
    private AutoCloseable autoCloseable;
    private Favorite favorite;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        favorite = new Favorite("path1/path2/path3", "dummy user", true);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testGetAllFavorites() {
        JSONObject matchJson = new JSONObject();
        matchJson.put("active", true);
        JSONObject queryJson = new JSONObject();
        queryJson.put("match", matchJson);
        JSONObject searchJson = new JSONObject();
        searchJson.put("query", queryJson);

        Mockito.when(favoriteClient.search(searchJson.toString())).thenReturn(getFavorites());

        List<FavoriteResponse> allFavorites = favoriteService.getAllFavorites();

        Mockito.verify(this.favoriteClient, Mockito.times(1))
                .search(ArgumentMatchers.anyString());

        assertNotNull(allFavorites);
        assertEquals("1234", allFavorites.get(0).getId());
        assertEquals("path1/path2/path3", allFavorites.get(0).getFilepath());
        assertEquals("dummy user", allFavorites.get(0).getUser());
        assertTrue(allFavorites.get(0).isActive());
    }

    private SearchResponse<Favorite> getFavorites() {
        Total total = new Total();
        total.setValue(1);
        total.setRelation("eq");

        Document<Favorite> document = new Document<>();
        document.setId("1234");
        document.setSource(favorite);

        Hits<Favorite> hits = new Hits<>();
        hits.setDocumentList(List.of(document));

        SearchResponse<Favorite> searchResponse = new SearchResponse<>();
        searchResponse.setHits(hits);

        return searchResponse;
    }

    private SearchResponse<Favorite> getEmptyFavorites() {
        Total total = new Total();
        total.setValue(0);
        total.setRelation("eq");

        Hits<Favorite> hits = new Hits<>();
        hits.setDocumentList(new ArrayList<>());

        SearchResponse<Favorite> searchResponse = new SearchResponse<>();
        searchResponse.setHits(hits);

        return searchResponse;
    }

    @Test
    void testMarkFavorite_createNew() throws JsonProcessingException {
        String filePath = "path1/path2/path3";

        JSONObject termJson = new JSONObject();
        termJson.put("filepath.keyword", filePath);
        JSONObject queryJson = new JSONObject();
        queryJson.put("term", termJson);
        JSONObject searchJson = new JSONObject();
        searchJson.put("query", queryJson);

        Mockito.when(favoriteClient.search(searchJson.toString())).thenReturn(getEmptyFavorites());
        Mockito.when(favoriteClient.create(favorite)).thenReturn("""
                {
                    "_index": "favorite",
                    "_id": "1234",
                    "_version": 1,
                    "result": "created",
                    "_shards": {
                        "total": 2,
                        "successful": 1,
                        "failed": 0
                    },
                    "_seq_no": 4,
                    "_primary_term": 2
                }""");

        FavoriteResponse favoriteResponse = favoriteService.markFavorite(filePath, true);
        Mockito.verify(this.favoriteClient, Mockito.times(1))
                .create(ArgumentMatchers.any(Favorite.class));

        assertNotNull(favoriteResponse);
        assertEquals("1234", favoriteResponse.getId());
        assertEquals("path1/path2/path3", favoriteResponse.getFilepath());
        assertEquals("dummy user", favoriteResponse.getUser());
        assertTrue(favoriteResponse.isActive());
    }

    @Test
    void testMarkFavorite_updateExisting() throws JsonProcessingException {
        String filePath = "path1/path2/path3";

        JSONObject termJson = new JSONObject();
        termJson.put("filepath.keyword", filePath);
        JSONObject queryJson = new JSONObject();
        queryJson.put("term", termJson);
        JSONObject searchJson = new JSONObject();
        searchJson.put("query", queryJson);

        Mockito.when(favoriteClient.search(searchJson.toString())).thenReturn(getFavorites());
        Mockito.doAnswer(Answers.CALLS_REAL_METHODS).when(favoriteClient).update("1234", favorite);

        FavoriteResponse favoriteResponse = favoriteService.markFavorite(filePath, true);
        Mockito.verify(this.favoriteClient, Mockito.times(1))
                .update(ArgumentMatchers.anyString(), ArgumentMatchers.any(Favorite.class));

        assertNotNull(favoriteResponse);
        assertEquals("1234", favoriteResponse.getId());
        assertEquals("path1/path2/path3", favoriteResponse.getFilepath());
        assertEquals("dummy user", favoriteResponse.getUser());
        assertTrue(favoriteResponse.isActive());
    }
}