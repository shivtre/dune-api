package com.wissen.tresata.web.model.opensearchResponse;

import lombok.Data;
import java.time.ZonedDateTime;

@Data
public class NoteResponse {

    private String id;

    private String productId;

    private String user;

    private String step;

    private String title;

    private String body;

    private ZonedDateTime created;

    private ZonedDateTime lastUpdated;
}
