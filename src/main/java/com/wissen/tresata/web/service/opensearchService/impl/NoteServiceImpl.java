package com.wissen.tresata.web.service.opensearchService.impl;

import com.wissen.tresata.web.client.opensearch.NoteClient;
import com.wissen.tresata.web.model.opensearchResponse.SearchResponse;
import com.wissen.tresata.web.model.opensearchResponse.Document;
import com.wissen.tresata.web.model.opensearchResponse.NoteResponse;
import com.wissen.tresata.web.model.opensearchResponse.NoteRequest;
import com.wissen.tresata.web.model.opensearchResponse.Note;
import com.wissen.tresata.web.service.opensearchService.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation class for getting, counting, inserting and updating Notes.
 *
 * @author Shubham Patel
 */

@Service
@Slf4j
public class NoteServiceImpl implements NoteService {
    @Autowired
    private NoteClient noteClient;

    /**
     * Method to get notes by id.
     *
     * @param id
     * @return NoteDTO
     */
    @Override
    public NoteResponse getNoteById(String id) {
        log.info("Getting a note by its unique id");

        return getNoteFromDocument(noteClient.getById(id));
    }

    /**
     * Method to get note dto from document.
     *
     * @param noteDocument
     * @return NoteDTO
     */
    private NoteResponse getNoteFromDocument(Document<Note> noteDocument) {
        NoteResponse noteResponse = new NoteResponse();
        noteResponse.setId(noteDocument.getId());
        noteResponse.setProductId(noteDocument.getSource().getProductId());
        noteResponse.setUser(noteDocument.getSource().getUser());
        noteResponse.setStep(noteDocument.getSource().getStep());
        noteResponse.setTitle(noteDocument.getSource().getTitle());
        noteResponse.setBody(noteDocument.getSource().getBody());
        noteResponse.setCreated(noteDocument.getSource().getCreated());
        noteResponse.setLastUpdated(noteDocument.getSource().getLastUpdated());

        return noteResponse;
    }

    /**
     * Method to get all the notes.
     *
     * @return list of NoteDTO
     */
    @Override
    public List<NoteResponse> getAllNotes() {
        log.info("Getting all the notes");

        SearchResponse<Note> noteSearchResponse = noteClient.search(new JSONObject().toString());
        return processSearchResponse(noteSearchResponse);
    }

    /**
     * Method to get notes in bulk.
     *
     * @param noteSearchResponse
     * @return list of NoteDTO
     */
    private List<NoteResponse> processSearchResponse(SearchResponse<Note> noteSearchResponse) {
        return noteSearchResponse.getHits().getDocumentList().stream()
                .map(this::getNoteFromDocument)
                .collect(Collectors.toList());
    }

    /**
     * Method to get the count of notes.
     *
     * @return count as long
     */
    @Override
    public Long count() {
        log.info("Total number of the notes");

        JSONObject countObject = new JSONObject(noteClient.count());
        return countObject.getLong("count");
    }

    /**
     * Method to delete the notes by id.
     *
     * @param id
     * @return String
     */
    @Override
    public String delete(String id) {
        log.info("Deleting a note by its unique id");

        noteClient.delete(id);
        return "Note successfully deleted!";
    }

    /**
     * Method to create a note.
     *
     * @param noteRequest
     * @return NoteDTO
     */
    @Override
    public NoteResponse createNote(NoteRequest noteRequest) {
        String user = "Dummy user";

        Note note = new Note();
        note.setProductId(noteRequest.getProductId());
        note.setTitle(noteRequest.getTitle());
        note.setBody(noteRequest.getBody());
        note.setStep(noteRequest.getStep());
        note.setLastUpdated(ZonedDateTime.now());
        note.setCreated(ZonedDateTime.now());
        note.setUser(user);

        String response = noteClient.create(note);
        return getNoteById(new JSONObject(response).getString("_id"));
    }

    /**
     * Method to update the notes.
     *
     * @param id
     * @param noteRequest
     * @return NoteDTO
     */
    @Override
    public NoteResponse update(String id, NoteRequest noteRequest) {
        NoteResponse noteResponse = getNoteById(id);

        String user = "Dummy user";

        Note note = new Note();
        note.setProductId(noteRequest.getProductId());
        note.setTitle(noteRequest.getTitle());
        note.setBody(noteRequest.getBody());
        note.setStep(noteRequest.getStep());
        note.setLastUpdated(ZonedDateTime.now());
        note.setCreated(noteResponse.getCreated());
        note.setUser(user);

        String response = noteClient.update(id, note);
        return getNoteById(new JSONObject(response).getString("_id"));
    }
}
