package com.wissen.tresata.web.controller.opensearchController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wissen.tresata.web.model.opensearchResponse.FavoriteResponse;
import com.wissen.tresata.web.service.opensearchService.FavoriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;


/**
 * Controller class for Favorite APIs.
 *
 * @author Anushka Saxena, Shubham Patel
 */

@RestController
@RequestMapping("/favorites")
@Slf4j
@CrossOrigin
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    /**
     * GET API to get all the favorite paths.
     *
     * @return favorite poths
     */
    @GetMapping("/getAllFavorites")
    public ResponseEntity<List<FavoriteResponse>> getAllFavorite() {
        log.info("Getting all favorites");
        return new ResponseEntity<>(favoriteService.getAllFavorites(), HttpStatus.OK);
    }

    /**
     * POST API to mark the path as favorite.
     *
     * @param filePath
     * @param status
     * @return favoritePath
     * @throws JsonProcessingException
     */
    @PostMapping("/markFavorite")
    public ResponseEntity<FavoriteResponse> markFavorite(String filePath, boolean status) throws JsonProcessingException {
        log.info("Performing Insert/Update for making the filepath as favorite/un-favorite");
        return new ResponseEntity<>(favoriteService.markFavorite(filePath, status), HttpStatus.OK);
    }
}