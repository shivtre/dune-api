package com.wissen.tresata.web.service.opensearchService;

import com.wissen.tresata.web.client.opensearch.NoteClient;
import com.wissen.tresata.web.model.opensearchResponse.*;
import com.wissen.tresata.web.service.opensearchService.impl.NoteServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Shubham Patel
 *
 * Test cases for NoteService
 */
class NoteServiceTest {

    @Mock
    private NoteClient noteClient;
    @InjectMocks
    private NoteServiceImpl noteService;
    private AutoCloseable autoCloseable;
    private Note note;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        note = new Note();
        note.setProductId("pid");
        note.setUser("Dummy user");
        note.setStep("Sourcing");
        note.setTitle("Title1");
        note.setBody("Body1");
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void teatGetNoteById_found() {
        Mockito.when(noteClient.getById("1234")).thenReturn(getNoteDocument());

        NoteResponse noteResponse = noteService.getNoteById("1234");
        Mockito.verify(this.noteClient, Mockito.times(1))
                .getById(ArgumentMatchers.anyString());

        assertNotNull(noteResponse);
        assertEquals("1234", noteResponse.getId());
        assertEquals("pid", noteResponse.getProductId());
        assertEquals("Dummy user", noteResponse.getUser());
        assertEquals("Sourcing", noteResponse.getStep());
        assertEquals("Title1", noteResponse.getTitle());
        assertEquals("Body1", noteResponse.getBody());
    }

    @Test
    void teatGetNoteById_notFound() {
        Mockito.when(noteClient.getById("1234")).thenReturn(getNotNoteDocument());

        assertThrows(NullPointerException.class, ()-> noteService.getNoteById("1234"));
        Mockito.verify(this.noteClient, Mockito.times(1))
                .getById(ArgumentMatchers.anyString());
    }

    private Document<Note> getNotNoteDocument() {
        Document<Note> noteDocument = new Document<>();
        noteDocument.setId("1234");
        noteDocument.setFound(false);

        return noteDocument;
    }

    private Document<Note> getNoteDocument() {
        Document<Note> noteDocument = new Document<>();
        noteDocument.setId("1234");
        noteDocument.setFound(true);
        noteDocument.setSource(note);

        return noteDocument;
    }

    @Test
    void testGetAllNotes() {
        Mockito.when(noteClient.search("{}")).thenReturn(getNotes());

        List<NoteResponse> noteResponseList = noteService.getAllNotes();
        Mockito.verify(this.noteClient, Mockito.times(1))
                .search(ArgumentMatchers.anyString());

        assertNotNull(noteResponseList);
        assertEquals("1234", noteResponseList.get(0).getId());
        assertEquals("pid", noteResponseList.get(0).getProductId());
        assertEquals("Dummy user", noteResponseList.get(0).getUser());
        assertEquals("Sourcing", noteResponseList.get(0).getStep());
        assertEquals("Title1", noteResponseList.get(0).getTitle());
        assertEquals("Body1", noteResponseList.get(0).getBody());
    }

    private SearchResponse<Note> getNotes() {
        Total total = new Total();
        total.setValue(1);
        total.setRelation("eq");

        Document<Note> document = new Document<>();
        document.setId("1234");
        document.setSource(note);

        Hits<Note> hits = new Hits<>();
        hits.setDocumentList(List.of(document));

        SearchResponse<Note> searchResponse = new SearchResponse<>();
        searchResponse.setHits(hits);

        return searchResponse;
    }

    @Test
    void testCount() {
        Mockito.when(noteClient.count()).thenReturn("""
                {
                    "count": 2,
                    "_shards": {
                        "total": 1,
                        "successful": 1,
                        "skipped": 0,
                        "failed": 0
                    }
                }""");

        Long count = noteService.count();
        Mockito.verify(this.noteClient, Mockito.times(1)).count();

        assertNotNull(count);
        assertEquals(2, count);
    }

    @Test
    void testDelete() {
        Mockito.doAnswer(Answers.CALLS_REAL_METHODS).when(noteClient).delete("1234");

        String message = noteService.delete("1234");
        Mockito.verify(this.noteClient, Mockito.times(1))
                .delete(ArgumentMatchers.anyString());

        assertNotNull(message);
        assertEquals("Note successfully deleted!", message);
    }

    @Test
    void teatCreateNote() {
        Mockito.when(noteClient.create(ArgumentMatchers.any(Note.class))).thenReturn("""
                {
                    "_index": "note",
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
        Mockito.when(noteClient.getById("1234")).thenReturn(getNoteDocument());

        NoteRequest noteRequest = new NoteRequest();
        noteRequest.setProductId("pid");
        noteRequest.setStep("Sourcing");
        noteRequest.setTitle("Title1");
        noteRequest.setBody("Body1");

        NoteResponse noteResponse = noteService.createNote(noteRequest);
        Mockito.verify(this.noteClient, Mockito.times(1))
                .create(ArgumentMatchers.any(Note.class));

        assertNotNull(noteResponse);
        assertEquals("1234", noteResponse.getId());
        assertEquals("pid", noteResponse.getProductId());
        assertEquals("Dummy user", noteResponse.getUser());
        assertEquals("Sourcing", noteResponse.getStep());
        assertEquals("Title1", noteResponse.getTitle());
        assertEquals("Body1", noteResponse.getBody());
    }

    @Test
    void update_foundAndUpdated() {
        Mockito.when(noteClient.update(ArgumentMatchers.anyString(), ArgumentMatchers.any(Note.class))).thenReturn("""
                {
                    "_index": "note",
                    "_id": "1234",
                    "_version": 2,
                    "result": "updated",
                    "_shards": {
                        "total": 2,
                        "successful": 1,
                        "failed": 0
                    },
                    "_seq_no": 5,
                    "_primary_term": 2
                }""");
        Mockito.when(noteClient.getById("1234")).thenReturn(getNoteDocument());

        NoteRequest noteRequest = new NoteRequest();
        noteRequest.setProductId("pid");
        noteRequest.setStep("Sourcing");
        noteRequest.setTitle("Title1");
        noteRequest.setBody("Body1");

        NoteResponse noteResponse = noteService.update("1234", noteRequest);
        Mockito.verify(this.noteClient, Mockito.times(1))
                .update(ArgumentMatchers.anyString(), ArgumentMatchers.any(Note.class));

        assertNotNull(noteResponse);
        assertEquals("1234", noteResponse.getId());
        assertEquals("pid", noteResponse.getProductId());
        assertEquals("Dummy user", noteResponse.getUser());
        assertEquals("Sourcing", noteResponse.getStep());
        assertEquals("Title1", noteResponse.getTitle());
        assertEquals("Body1", noteResponse.getBody());
    }

    @Test
    void update_notFound() {
        Mockito.when(noteClient.getById("1234")).thenReturn(getNotNoteDocument());

        assertThrows(NullPointerException.class, ()-> noteService.getNoteById("1234"));
        Mockito.verify(this.noteClient, Mockito.times(0))
                .update(ArgumentMatchers.anyString(), ArgumentMatchers.any(Note.class));
    }
}