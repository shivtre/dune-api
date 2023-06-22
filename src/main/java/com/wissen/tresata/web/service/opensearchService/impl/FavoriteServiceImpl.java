package com.wissen.tresata.web.service.opensearchService.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wissen.tresata.web.client.opensearch.FavoriteClient;
import com.wissen.tresata.web.model.opensearchResponse.SearchResponse;
import com.wissen.tresata.web.model.opensearchResponse.FavoriteResponse;
import com.wissen.tresata.web.model.opensearchResponse.Favorite;
import com.wissen.tresata.web.service.opensearchService.FavoriteService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation class for getting, inserting and updating favorites.
 *
 * @author Anushka Saxena, Shubham Patel
 */

@Service
@Slf4j
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteClient favoriteClient;

    /**
     * Method to find if path is favorite.
     *
     * @param filePath
     * @return FavoriteResponse
     */
    private FavoriteResponse findByFilePath(String filePath) {
        JSONObject termJson = new JSONObject();
        termJson.put("filepath.keyword", filePath);
        JSONObject queryJson = new JSONObject();
        queryJson.put("term", termJson);
        JSONObject searchJson = new JSONObject();
        searchJson.put("query", queryJson);

        SearchResponse<Favorite> favoriteSearchResponse = favoriteClient.search(searchJson.toString());
        List<FavoriteResponse> favoriteList = processSearchResponse(favoriteSearchResponse);
        if (!favoriteList.isEmpty()) {
            return favoriteList.get(0);
        } else {
            return null;
        }
    }

    /**
     * Method to get all the favorite paths.
     *
     * @return list of FavoriteResponse
     */
    @Override
    public List<FavoriteResponse> getAllFavorites() {
        log.info("Getting all filepath marked as Favorite");

        JSONObject matchJson = new JSONObject();
        matchJson.put("active", true);
        JSONObject queryJson = new JSONObject();
        queryJson.put("match", matchJson);
        JSONObject searchJson = new JSONObject();
        searchJson.put("query", queryJson);

        SearchResponse<Favorite> favoriteSearchResponse = favoriteClient.search(searchJson.toString());
        return processSearchResponse(favoriteSearchResponse);
    }

    /**
     * Method to get favorite paths in bulk.
     *
     * @param favoriteSearchResponse
     * @return list of SearchResponse
     */
    private List<FavoriteResponse> processSearchResponse(SearchResponse<Favorite> favoriteSearchResponse) {
        return favoriteSearchResponse.getHits().getDocumentList().stream().map(d -> {
            FavoriteResponse favoriteResponse = new FavoriteResponse();
            favoriteResponse.setId(d.getId());
            favoriteResponse.setFilepath(d.getSource().getFilepath());
            favoriteResponse.setUser(d.getSource().getUser());
            favoriteResponse.setActive(d.getSource().isActive());
            return favoriteResponse;
        }).collect(Collectors.toList());
    }

    /**
     * Method to mark the paths as favorite.
     *
     * @param filePath
     * @param favoriteStatus
     * @return FavoriteDTO
     * @throws JsonProcessingException
     */
    @Override
    public FavoriteResponse markFavorite(String filePath, boolean favoriteStatus) throws JsonProcessingException {
        log.info("Marking a filepath as Favorite");

        //TODO: Replace user from JWT
        String user = "dummy user";

        Favorite favorite = new Favorite(filePath, user, favoriteStatus);
        FavoriteResponse tempFavorite = findByFilePath(filePath);

        if (tempFavorite != null) {
            favoriteClient.update(tempFavorite.getId(), favorite);
            return new FavoriteResponse(tempFavorite.getId(), filePath, user, favoriteStatus);
        } else {
            String response = favoriteClient.create(favorite);
            return new FavoriteResponse(new JSONObject(response).getString("_id"),
                    filePath, user, favoriteStatus);
        }
    }

}
