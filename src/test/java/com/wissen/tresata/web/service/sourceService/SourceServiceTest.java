package com.wissen.tresata.web.service.sourceService;

import com.wissen.tresata.web.client.sourceClient.SourceClient;
import com.wissen.tresata.web.model.sourceResponse.FileDetails;
import com.wissen.tresata.web.model.sourceResponse.SourceResponse;
import com.wissen.tresata.web.service.sourceService.impl.SourceServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Anushka Saxena, Shubham Patel
 *
 * Test cases for DuneDataService
 */

class SourceServiceTest {

    @Mock
    private SourceClient sourceClient;

    @InjectMocks
    private SourceServiceImpl sourceService;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void initMocks(){
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getListFilesTest(){
        Mockito.when(sourceClient.listFiles("path-1","token")).thenReturn(getResponse());

        SourceResponse response = sourceService.getListFiles("path-1", "token");

        Mockito.verify(sourceClient, Mockito.times(1))
                .listFiles(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());

        assertNotNull(response);
        assertEquals("path-1", response.getFileDetailsList().get(0).getPath());
        assertTrue(response.getFileDetailsList().get(0).isDir());

        assertEquals("path-2", response.getFileDetailsList().get(1).getPath());
        assertFalse(response.getFileDetailsList().get(1).isDir());

    }

    private SourceResponse getResponse(){
        SourceResponse lsPathResponse = new SourceResponse();
        List<FileDetails> files = new ArrayList<>();

        files.add(new FileDetails(true, "path-1"));
        files.add(new FileDetails(false, "path-2"));

        lsPathResponse.setFileDetailsList(files);

        return lsPathResponse;
    }

    @Test
    void testGetListFiles() {
        Mockito.when(sourceClient.schema("path1/path2/file.csv", "csv", "token"))
                .thenReturn("{}");

        String response = sourceService.getSchema("path1/path2/file.csv", "csv", "token");

        Mockito.verify(sourceClient, Mockito.times(1))
                .schema(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
        assertNotNull(response);
        assertEquals("{}", response);
    }

    @Test
    void testGetData() {
        Mockito.when(sourceClient.data("path1/path2/file.csv", "csv", 10, "token"))
                .thenReturn("{}");

        String response = sourceService.getData("path1/path2/file.csv", "csv", 10, "token");

        Mockito.verify(sourceClient, Mockito.times(1))
                .data(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyString());
        assertNotNull(response);
        assertEquals("{}", response);
    }
}
