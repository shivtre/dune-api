package com.wissen.tresata.web.util;

import com.wissen.tresata.web.model.AuthData;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import java.util.Arrays;
import java.util.Base64;

/**
 * Util class.
 *
 * @author Anushka Saxena
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DuneUtility {

    /**
     * Method to get the Auth header Value.
     *
     * @param request
     * @return AuthData
     */
    public static AuthData getAuthHeaderValue(HttpServletRequest request) {

        AuthData authData = new AuthData();
        String authorizationHeader = request.getHeader("Authorization");

        if (StringUtils.startsWith(authorizationHeader, "Bearer ")) {
            String token = authorizationHeader.substring(7);
            String[] tokenParts = token.split("\\.");

            authData.setToken(token);
            authData.setBearerToken(authorizationHeader);

            if (tokenParts.length >= 2) {
                String payload = new String(Base64.getDecoder().decode(tokenParts[1]));
                JSONObject payloadJson = new JSONObject(payload);

                if (payloadJson.has("sub")) {
                    authData.setSub(payloadJson.getString("sub"));
                }
                if (payloadJson.has("cognito:groups")) {
                    authData.setGroups(Arrays.asList(payloadJson.getJSONArray("cognito:groups").getString(0)));
                }
                if (payloadJson.has("email_verified")) {
                    authData.setEmailVerified(payloadJson.getBoolean("email_verified"));
                }
                if (payloadJson.has("iss")) {
                    authData.setIss(payloadJson.getString("iss"));
                }
                if (payloadJson.has("custom:group")) {
                    authData.setCustomGroup(payloadJson.getString("custom:group"));
                }
                if (payloadJson.has("cognito:username")) {
                    authData.setUsername(payloadJson.getString("cognito:username"));
                }
                if (payloadJson.has("aud")) {
                    authData.setAud(payloadJson.getString("aud"));
                }
                if (payloadJson.has("event_id")) {
                    authData.setEventId(payloadJson.getString("event_id"));
                }
                if (payloadJson.has("token_use")) {
                    authData.setTokenUse(payloadJson.getString("token_use"));
                }
                if (payloadJson.has("auth_time")) {
                    authData.setAuthTime(payloadJson.getLong("auth_time"));
                }
                if (payloadJson.has("exp")) {
                    authData.setExp(payloadJson.getLong("exp"));
                }
                if (payloadJson.has("custom:role")) {
                    authData.setCustomRole(payloadJson.getString("custom:role"));
                }
                if (payloadJson.has("iat")) {
                    authData.setIat(payloadJson.getLong("iat"));
                }
                if (payloadJson.has("email")) {
                    authData.setEmail(payloadJson.getString("email"));
                }
            }
        } else {
            throw new RuntimeException("Authorization header is not valid");
        }

        return authData;
    }
}
