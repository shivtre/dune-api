package com.wissen.tresata.web.model.opensearchResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FavoriteResponse {

    private String id;

    private String filepath;

    private String user;

    private boolean active;

}
