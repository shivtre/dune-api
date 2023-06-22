package com.wissen.tresata.web.client.opensearch;

import com.wissen.tresata.web.model.opensearchResponse.SearchResponse;
import com.wissen.tresata.web.model.opensearchResponse.Document;
import com.wissen.tresata.web.config.FeignSSLConfiguration;
import com.wissen.tresata.web.model.opensearchResponse.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

/**
 * Feign Client Interface for note index of OpenSearch.
 *
 * @author Shubham Patel
 */

@FeignClient(value = "note", url = "${opensearch.url}", configuration = FeignSSLConfiguration.class)
public interface NoteClient {

    /**
     * GET API to get the notes by id.
     *
     * @param id
     * @return notes
     */
    @GetMapping("/note/_doc/{id}")
    Document<Note> getById(@PathVariable("id") String id);

    /**
     * POST API to search the notes.
     *
     * @param jsonPayload
     * @return notes
     */
    @PostMapping(value = "/note/_search", consumes = MediaType.APPLICATION_JSON_VALUE)
    SearchResponse<Note> search(@RequestBody String jsonPayload);

    /**
     * POST API to create a note.
     *
     * @param note
     * @return note
     */
    @PostMapping("/note/_doc")
    String create(@RequestBody Note note);

    /**
     * POST API to update the note by id.
     *
     * @param id
     * @param note
     * @return note
     */
    @PostMapping("/note/_doc/{id}")
    String update(@PathVariable String id, @RequestBody Note note);

    /**
     * DELETE API to delete the note by id.
     *
     * @param id
     * @return note
     */
    @DeleteMapping("/note/_doc/{id}")
    String delete(@PathVariable("id") String id);

    /**
     * GET API to get the count.
     *
     * @return count
     */
    @GetMapping("/note/_count")
    String count();
}
