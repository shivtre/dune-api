package com.wissen.tresata.web.model.sourceResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * Get Response for ls API as a list.
 *
 * @author Anushka Saxena
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SourceResponse {

    @JsonProperty("files")
    private List<FileDetails> fileDetailsList;

}
