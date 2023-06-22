package com.wissen.tresata.web.service.sourceService;

import com.wissen.tresata.web.model.sourceResponse.SourceResponse;

/**
 * Service Interface for ls API.
 *
 * @author Anushka Saxena, Shubham Patel
 */

public interface SourceService {

    /**
     * Method to get the list of files and directories.
     *
     * @param path
     * @param bearerToken
     * @return SourceResponse
     */
    SourceResponse getListFiles(String path, String bearerToken);

    /**
     * Method to get the schema.
     *
     * @param path
     * @param format
     * @param bearerToken
     * @return schema as string
     */
    String getSchema(String path, String format, String bearerToken);

    /**
     * Method to get the data.
     *
     * @param path
     * @param format
     * @param count
     * @param bearerToken
     * @return data as string
     */
    String getData(String path, String format, Integer count, String bearerToken);
}
