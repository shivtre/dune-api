package com.wissen.tresata.web.model.opensearchResponse;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchResponse<T> {

    private Hits<T> hits;
}
