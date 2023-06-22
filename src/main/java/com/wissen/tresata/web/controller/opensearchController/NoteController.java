package com.wissen.tresata.web.controller.opensearchController;

import com.wissen.tresata.web.model.opensearchResponse.NoteResponse;
import com.wissen.tresata.web.model.opensearchResponse.NoteRequest;
import com.wissen.tresata.web.service.opensearchService.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.List;

/**
 * Controller class for Note APIs.
 *
 * @author Shubham Patel
 */

@RestController
@CrossOrigin
@RequestMapping("/notes")
@Slf4j
public class NoteController {

    @Autowired
    private NoteService noteService;

    /**
     * GET API to get the note by id.
     *
     * @param id
     * @return NoteDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<NoteResponse> getNote(@PathVariable String id) {
        log.info("Getting note by id");
        return new ResponseEntity<>(noteService.getNoteById(id), HttpStatus.OK);
    }

    /**
     * GET API to get all the notes.
     *
     * @return notes
     */
    @GetMapping
    public ResponseEntity<List<NoteResponse>> getAllNotes() {
        log.info("Getting all notes");
        return new ResponseEntity<>(noteService.getAllNotes(), HttpStatus.OK);
    }

    /**
     * GET API to get the count of notes.
     *
     * @return count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        log.info("counting all notes");
        return ResponseEntity.ok(noteService.count());
    }

    /**
     * POST API to create a new note.
     *
     * @param noteRequest
     * @return note
     */
    @PostMapping
    public ResponseEntity<NoteResponse> createNote(@RequestBody NoteRequest noteRequest) {
        log.info("Creating a new note");
        return new ResponseEntity<>(noteService.createNote(noteRequest), HttpStatus.CREATED);
    }

    /**
     * POST API to update the note by id.
     *
     * @param id
     * @param noteRequest
     * @return note
     */
    @PutMapping("/{id}")
    public ResponseEntity<NoteResponse> updateNote(@PathVariable String id, @RequestBody NoteRequest noteRequest) {
        log.info("Updating a note by id");
        return new ResponseEntity<>(noteService.update(id, noteRequest), HttpStatus.OK);
    }

    /**
     * DELETE API to delete the note by id.
     *
     * @param id
     * @return String
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNote(@PathVariable String id) {
        log.info("Deleting a note by id");
        return new ResponseEntity<>(noteService.delete(id), HttpStatus.OK);
    }
}
