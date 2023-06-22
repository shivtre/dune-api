package com.wissen.tresata.web.model.opensearchResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class Hits<T> {
    private Total total;

    @JsonProperty("hits")
    private List<Document<T>> documentList;
}
