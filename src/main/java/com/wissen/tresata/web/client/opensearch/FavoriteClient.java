package com.wissen.tresata.web.client.opensearch;

import com.wissen.tresata.web.model.opensearchResponse.SearchResponse;
import com.wissen.tresata.web.config.FeignSSLConfiguration;
import com.wissen.tresata.web.model.opensearchResponse.Favorite;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Feign Client Interface for favorite index of OpenSearch.
 *
 * @author Shubham Patel
 */

@FeignClient(value = "favorite", url = "${opensearch.url}", configuration = FeignSSLConfiguration.class)
public interface FavoriteClient {

    /**
     * POST API for searching the favorite path.
     *
     * @param jsonPayload
     * @return favorite
     */
    @PostMapping(value = "/favorite/_search", consumes = MediaType.APPLICATION_JSON_VALUE)
    SearchResponse<Favorite> search(@RequestBody String jsonPayload);

    /**
     * POST API to update the favorite path.
     *
     * @param id
     * @param favorite
     */
    @PostMapping("/favorite/_doc/{id}")
    void update(@PathVariable String id, @RequestBody Favorite favorite);

    /**
     * POST API to create a new favorite.
     *
     * @param favorite
     * @return favorite
     */
    @PostMapping("/favorite/_doc")
    String create(@RequestBody Favorite favorite);
}
