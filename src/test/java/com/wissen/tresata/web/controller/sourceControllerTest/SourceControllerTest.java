package com.wissen.tresata.web.controller.sourceControllerTest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wissen.tresata.web.controller.sourceController.SourceController;
import com.wissen.tresata.web.model.sourceResponse.FileDetails;
import com.wissen.tresata.web.model.sourceResponse.SourceResponse;
import com.wissen.tresata.web.service.sourceService.SourceService;
import jakarta.servlet.ServletException;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Anushka Saxena, Shubham Patel
 *
 * Junit Test class for ls DuneDataController
 */


public class SourceControllerTest {

    private static final String BASE_URL = "/source";

    private MockMvc mockMvc;

    @Mock
    SourceService sourceService;

    @InjectMocks
    SourceController sourceController;

    @BeforeEach
    void initMocks(){
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.sourceController).build();
    }

    @Test
    public void testGetListFiles() throws Exception {
        Mockito.when(sourceService.getListFiles("abc", "Bearer token")).thenReturn(getResponse());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/ls")
                        .param("path", "abc")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();
        SourceResponse response = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {});

        Mockito.verify(sourceService, Mockito.times(1)).getListFiles(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());

        assertNotNull(response);
        assertEquals("path-1", response.getFileDetailsList().get(0).getPath());
        assertTrue(response.getFileDetailsList().get(0).isDir());

        assertEquals("path-2", response.getFileDetailsList().get(1).getPath());
        assertFalse(response.getFileDetailsList().get(1).isDir());
    }

    @Test
    void getListFilesTestWithInvalidAccessToken() throws Exception{

        assertThrows(ServletException.class , () -> this.mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/ls?path=abc")
                        .header("Authorization", "token"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn());


        Mockito.verify(this.sourceService, Mockito.times(0)).getListFiles(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
    }

    private SourceResponse getResponse(){

        SourceResponse sourceResponse = new SourceResponse();
        List<FileDetails> files = new ArrayList<>();

        files.add(new FileDetails(true, "path-1"));
        files.add(new FileDetails(false, "path-2"));

        sourceResponse.setFileDetailsList(files);

        return sourceResponse;

    }

    @Test
    void testGetSchema() throws Exception {
        Mockito.when(sourceService.getSchema("path1/path2/file.csv", "csv", "Bearer token")).thenReturn("{}");

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/schema")
                        .param("path", "path1/path2/file.csv")
                        .param("format", "csv")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(sourceService, Mockito.times(1))
                .getSchema(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
    }

    @Test
    void testGetSchema_invalidAccessToken() {
        assertThrows(ServletException.class , () -> this.mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/schema")
                        .param("path", "path1/path2/file.csv")
                        .param("format", "csv")
                        .header("Authorization", "token"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn());

        Mockito.verify(sourceService, Mockito.times(0))
                .getSchema(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
    }

    @Test
    void testGetData() throws Exception {
        Mockito.when(sourceService.getData("path1/path2/file.csv", "csv", 10, "Bearer token")).thenReturn(new JSONArray().toString());

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/data")
                        .param("path", "path1/path2/file.csv")
                        .param("format", "csv")
                        .param("count", "10")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(sourceService, Mockito.times(1))
                .getData(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyString());
    }

    @Test
    void testGetData_invalidAccessToken() {
        assertThrows(ServletException.class , () -> this.mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/data")
                        .param("path", "path1/path2/file.csv")
                        .param("format", "csv")
                        .param("count", "10")
                        .header("Authorization", "token"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn());

        Mockito.verify(sourceService, Mockito.times(0))
                .getSchema(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
    }
}