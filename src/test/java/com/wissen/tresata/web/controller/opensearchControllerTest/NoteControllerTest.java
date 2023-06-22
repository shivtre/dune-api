package com.wissen.tresata.web.controller.opensearchControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wissen.tresata.web.controller.opensearchController.NoteController;
import com.wissen.tresata.web.model.opensearchResponse.NoteResponse;
import com.wissen.tresata.web.model.opensearchResponse.NoteRequest;
import com.wissen.tresata.web.service.opensearchService.NoteService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Shubham Patel
 *
 * Test cases for NoteController
 */

class NoteControllerTest {

    private MockMvc mockMvc;
    @Mock
    private NoteService noteService;
    @InjectMocks
    private NoteController noteController;
    private NoteResponse noteResponse;
    private final String BASE_URL = "/notes";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.noteController).build();

        noteResponse = new NoteResponse();
        noteResponse.setId("1234");
        noteResponse.setProductId("pid");
        noteResponse.setUser("Dummy user");
        noteResponse.setStep("Sourcing");
        noteResponse.setTitle("Title1");
        noteResponse.setBody("Body1");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetNote() throws Exception {
        Mockito.when(noteService.getNoteById("1234")).thenReturn(noteResponse);

        this.mockMvc.perform(get(BASE_URL + "/1234"))
                .andDo(print()).andExpect(status().isOk());
        Mockito.verify(this.noteService, Mockito.times(1))
                .getNoteById(ArgumentMatchers.anyString());
    }

    @Test
    void testGetAllNotes() throws Exception {
        Mockito.when(noteService.getAllNotes()).thenReturn(List.of(noteResponse));

        this.mockMvc.perform(get(BASE_URL))
                .andDo(print()).andExpect(status().isOk());
        Mockito.verify(this.noteService, Mockito.times(1)).getAllNotes();
    }

    @Test
    void testCount() throws Exception {
        Mockito.when(noteService.count()).thenReturn(2L);

        this.mockMvc.perform(get(BASE_URL + "/count"))
                .andDo(print()).andExpect(status().isOk());
        Mockito.verify(this.noteService, Mockito.times(1)).count();
    }

    @Test
    void createNote() throws Exception {
        NoteRequest noteRequest = new NoteRequest();
        noteRequest.setProductId("pid");
        noteRequest.setStep("Sourcing");
        noteRequest.setTitle("Title1");
        noteRequest.setBody("Body1");

        Mockito.when(noteService.createNote(noteRequest)).thenReturn(noteResponse);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String json = writer.writeValueAsString(noteRequest);

        this.mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print()).andExpect(status().isCreated());
        Mockito.verify(this.noteService, Mockito.times(1))
                .createNote(ArgumentMatchers.any(NoteRequest.class));
    }

    @Test
    void updateNote() throws Exception {
        NoteRequest noteRequest = new NoteRequest();
        noteRequest.setProductId("pid");
        noteRequest.setStep("Sourcing");
        noteRequest.setTitle("Title1");
        noteRequest.setBody("Body1");

        Mockito.when(noteService.update("1234", noteRequest)).thenReturn(noteResponse);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String json = writer.writeValueAsString(noteRequest);

        this.mockMvc.perform(put(BASE_URL + "/1234")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print()).andExpect(status().isOk());
        Mockito.verify(this.noteService, Mockito.times(1))
                .update(ArgumentMatchers.anyString(), ArgumentMatchers.any(NoteRequest.class));
    }

    @Test
    void deleteNote() throws Exception {
        Mockito.when(noteService.delete("1234")).thenReturn("Note successfully deleted!");

        this.mockMvc.perform(delete(BASE_URL + "/1234"))
                .andDo(print()).andExpect(status().isOk());
        Mockito.verify(this.noteService, Mockito.times(1))
                .delete(ArgumentMatchers.anyString());
    }
}
