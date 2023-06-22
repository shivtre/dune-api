package com.wissen.tresata.web.service.opensearchService;

import com.wissen.tresata.web.model.opensearchResponse.NoteResponse;
import com.wissen.tresata.web.model.opensearchResponse.NoteRequest;
import java.util.List;

/**
 * Interface for getting, counting, inserting and updating Notes.
 *
 * @author Shubham Patel
 */

public interface NoteService {

    /**
     * Method to get the notes by id.
     *
     * @param id
     * @return NoteDTO
     */
    NoteResponse getNoteById(String id);

    /**
     * Method to get all the notes.
     *
     * @return list of NoteDTO
     */
    List<NoteResponse> getAllNotes();

    /**
     * Method to get the count of notes.
     *
     * @return count as long
     */
    Long count();

    /**
     * Method to delete the notes by id.
     *
     * @param id
     * @return String
     */
    String delete(String id);

    /**
     * Method to create the notes.
     *
     * @param noteRequest
     * @return NoteDTO
     */
    NoteResponse createNote(NoteRequest noteRequest);

    /**
     * Method to update the notes by id.
     *
     * @param id
     * @param noteRequest
     * @return NoteDTO
     */
    NoteResponse update(String id, NoteRequest noteRequest);
}
