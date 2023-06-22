package com.wissen.tresata.web.model.opensearchResponse;

import lombok.Data;

@Data
public class NoteRequest {

    private String productId;

    private String step;

    private String title;

    private String body;
}
