package com.wissen.tresata.web.service.sourceService.impl;

import com.wissen.tresata.web.client.sourceClient.SourceClient;
import com.wissen.tresata.web.model.sourceResponse.SourceResponse;
import com.wissen.tresata.web.service.sourceService.SourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for ls API.
 *
 * @author Anushka Saxena, Shubham Patel
 */

@Service
@Slf4j
public class SourceServiceImpl implements SourceService {

    @Autowired
    private SourceClient sourceClient;

    /**
     * Method to get the list of files and directories.
     *
     * @param path
     * @param bearerToken
     * @return SourceResponse
     */

    @Override
    public SourceResponse getListFiles(String path, String bearerToken) {
        log.info("Listing all the folders and files from a path");
        return sourceClient.listFiles(path, bearerToken);
    }

    /**
     * Method to get the schema from opensearch.
     *
     * @param path
     * @param format
     * @param bearerToken
     * @return schema as string
     */
    @Override
    public String getSchema(String path, String format, String bearerToken) {
        log.info("Getting the schema from a file");
        return sourceClient.schema(path, format, bearerToken);
    }

    /**
     * Method to get the data from opensearch.
     *
     * @param path
     * @param format
     * @param count
     * @param bearerToken
     * @return data as string
     */
    @Override
    public String getData(String path, String format, Integer count, String bearerToken) {
        log.info("Getting n data from for the schema from a file");
        return sourceClient.data(path, format, count, bearerToken);
    }
}
