package com.wissen.tresata.web.service.opensearchService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wissen.tresata.web.model.opensearchResponse.FavoriteResponse;
import java.util.List;

/**
 * Interface for getting, inserting and updating favorite.
 *
 * @author Anushka Saxena
 */

public interface FavoriteService {

    /**
     * Method to get all teh favorites.
     *
     * @return list of FavoriteDTO
     */
    List<FavoriteResponse> getAllFavorites();

    /**
     * Method to mark the paths as favorite.
     *
     * @param filePath
     * @param favoriteStatus
     * @return FavoriteDTO
     * @throws JsonProcessingException
     */
    FavoriteResponse markFavorite(String filePath, boolean favoriteStatus) throws JsonProcessingException;

}
