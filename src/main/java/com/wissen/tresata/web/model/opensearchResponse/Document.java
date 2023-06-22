package com.wissen.tresata.web.model.opensearchResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Document<T> {
    @JsonProperty("_id")
    private String id;

    private Boolean found;

    @JsonProperty("_seq_no")
    private String seqNo;

    @JsonProperty("_primary_term")
    private String primaryTerm;

    @JsonProperty("_source")
    private T source;

}
