package com.wissen.tresata.web.client.sourceClient;

import com.wissen.tresata.web.model.sourceResponse.SourceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign Client Interface for Dune APIs.
 *
 * @author Anushka Saxena, Shubham Patel
 */


@FeignClient(name = "source", url = "${dune.client.url}")
public interface SourceClient {

    /**
     * Method to list the directories and files.
     *
     * @param path
     * @param token
     * @return SourceResponse
     */
    @GetMapping("/ls")
    SourceResponse listFiles(@RequestParam("path") String path,
                             @RequestHeader("Authorization") String token);

    /**
     * Method to get the schema.
     *
     * @param path
     * @param format
     * @param token
     * @return schema as string
     */
    @GetMapping("/schema")
    String schema(@RequestParam("path")String path,
                  @RequestParam("format")String format,
                  @RequestHeader("Authorization") String token);

    /**
     * Method to get the data from opensearch.
     *
     * @param path
     * @param format
     * @param count
     * @param token
     * @return data
     */
    @GetMapping("/head")
    String data(@RequestParam("path")String path,
                @RequestParam("format")String format,
                @RequestParam("lines")Integer count,
                @RequestHeader("Authorization") String token);
}
