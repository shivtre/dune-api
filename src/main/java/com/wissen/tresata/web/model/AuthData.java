package com.wissen.tresata.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * Pojo for Authorization Data.
 *
 * @author Anushka Saxena
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthData {

    private String bearerToken;

    private String token;

    private String sub;

    private List<String> groups;

    private Boolean emailVerified;

    private String iss;

    private String customGroup;

    private String username;

    private String aud;

    private String eventId;

    private String tokenUse;

    private Long authTime;

    private Long exp;

    private String customRole;

    private Long iat;

    private String email;

}
