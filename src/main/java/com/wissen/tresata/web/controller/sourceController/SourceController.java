package com.wissen.tresata.web.controller.sourceController;

import com.wissen.tresata.web.model.sourceResponse.SourceResponse;
import com.wissen.tresata.web.model.AuthData;
import com.wissen.tresata.web.service.sourceService.SourceService;
import com.wissen.tresata.web.util.DuneUtility;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for ls API wrapper.
 *
 * @author Anushka Saxena, Shubham Patel
 */
@RestController
@RequestMapping("/source")
@Slf4j
public class SourceController {

    @Autowired
    private SourceService sourceService;


    /**
     * GET API to get the list of files and directories.
     *
     * @param path
     * @param request
     * @return SourceResponse
     */
    @GetMapping("/ls")
    public ResponseEntity<SourceResponse> getListFiles(String path, HttpServletRequest request) {
        log.info("Listing all the folders and files from a path");
        AuthData authData = DuneUtility.getAuthHeaderValue(request);
        return new ResponseEntity<>(sourceService.getListFiles(path, authData.getBearerToken()), HttpStatus.OK);
    }

    /**
     * GET API to get the schema from opensearch.
     *
     * @param path
     * @param format
     * @param request
     * @return schema
     */
    @GetMapping(value = "/schema", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSchema(String path, String format, HttpServletRequest request) {
        log.info("Getting the schema from a file");
        AuthData authData = DuneUtility.getAuthHeaderValue(request);
        return new ResponseEntity<>(sourceService.getSchema(path, format, authData.getBearerToken()), HttpStatus.OK);
    }

    /**
     * GET API to get the data from opensearch.
     *
     * @param path
     * @param format
     * @param count
     * @param request
     * @return data
     */
    @GetMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getData(String path, String format, Integer count, HttpServletRequest request) {
        log.info("Getting n data from for the schema from a file");
        AuthData authData = DuneUtility.getAuthHeaderValue(request);
        return new ResponseEntity<>(sourceService.getData(path, format, count, authData.getBearerToken()), HttpStatus.OK);
    }
}
