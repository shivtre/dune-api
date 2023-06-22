package com.wissen.tresata.web.model.sourceResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Get response data for ls API.
 *
 * @author Anushka Saxena
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDetails {

    @JsonProperty("isDir")
    private boolean dir;

    private String path;

}
