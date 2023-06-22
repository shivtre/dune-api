package com.wissen.tresata.web.model.opensearchResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
public class Note {

    @JsonProperty("product_id")
    private String productId;

    private String user;

    private String step;

    private String title;

    private String body;

    private ZonedDateTime created;

    @JsonProperty("last_updated")
    private ZonedDateTime lastUpdated;
}
