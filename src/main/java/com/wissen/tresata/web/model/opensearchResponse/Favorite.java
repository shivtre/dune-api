package com.wissen.tresata.web.model.opensearchResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * POJO for Favorite.
 *
 * @author Anushka Saxena
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Favorite {

    private String filepath;

    private String user;

    private boolean active;

}
