package com.wissen.tresata.web.util;

import com.wissen.tresata.web.model.AuthData;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * JUNIT Test case for DuneUtility.
 *
 * @author Anushka Saxena
 */
public class DuneUtilityTest {

    @Test
    public void testGetAuthHeaderValue() {

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        String actualToken = "eyJraWQiOiJsZzh1S3QwVkZ2ZVE2aSttYzRrMXo5OEkzeDBtS0dxWU5jZDNJTHUxSXBzPSIsImFsZyI6IlJTMjU2In0" +
                ".eyJzdWIiOiI4ZGU3ZDc4Zi1hZDgxLTQyZWEtYjYwMS00YzNiZmM3ZjMzOWQiLCJjb2duaXRvOmdyb3VwcyI6WyJ0aGVvbmUiXSwiZW1ha" +
                "WxfdmVyaWZpZWQiOnRydWUsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy1lYXN0LTEuYW1hem9uYXdzLmNvbVwvdXMtZWFzdC0x" +
                "X1EzSUk0bVVrZiIsImN1c3RvbTpncm91cCI6InRoZW9uZSIsImNvZ25pdG86dXNlcm5hbWUiOiJhbnVzaGthIiwiYXVkIjoiNXY2Nmc2b2tl" +
                "OTBlbmJtb2pnNmZzZzI5OTkiLCJldmVudF9pZCI6IjZmMTQyNGFiLWI0MjItNGY4MC04YzMzLTUyMmE3ZGEzOWVkYyIsInRva2VuX3VzZSI6Im" +
                "lkIiwiYXV0aF90aW1lIjoxNjg2NzI0NTg4LCJleHAiOjE2ODY3MjgxODgsImN1c3RvbTpyb2xlIjoidGVuYW50IiwiaWF0IjoxNjg2NzI0NTg4L" +
                "CJlbWFpbCI6ImFudXNoa2Euc2F4ZW5hQHRyZXNhdGEuY29tIn0.ih4Y2b-LufLvvfkmzHCARnI2Cz3BDqT_pQ-i6Kzq9rdG0YQZho7MZGvIM26_0" +
                "jUwlQqws4GV_lSlShvSG6KB6GjjKNFu2h_lGLbBw7PstnBjbqJpEfE8tPB9afm2VsI6FKCB4xckYvAlb3F0joScDWcHWiVgNpneB5XG2OVcUiCogiA" +
                "GpW3v7t6MrENhTop57TASbxfXi8KhAzoeN6s7tCX6_hUHXw-IUePSH9Ib3kvGWRWS9lLO-L8SqP-UIyzP6ABtPN8NNM0oWeyOtXqdwzYFWAqy1SI6k14" +
                "xKPT-g5lvSRdJsZabGu9V51SjVTDfIi9BohJjHWM6D6Zsgdgsow";
        String expectedToken = "eyJraWQiOiJsZzh1S3QwVkZ2ZVE2aSttYzRrMXo5OEkzeDBtS0dxWU5jZDNJTHUxSXBzPSIsImFsZyI6IlJTMjU2In0" +
                ".eyJzdWIiOiI4ZGU3ZDc4Zi1hZDgxLTQyZWEtYjYwMS00YzNiZmM3ZjMzOWQiLCJjb2duaXRvOmdyb3VwcyI6WyJ0aGVvbmUiXSwiZW1ha" +
                "WxfdmVyaWZpZWQiOnRydWUsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy1lYXN0LTEuYW1hem9uYXdzLmNvbVwvdXMtZWFzdC0x" +
                "X1EzSUk0bVVrZiIsImN1c3RvbTpncm91cCI6InRoZW9uZSIsImNvZ25pdG86dXNlcm5hbWUiOiJhbnVzaGthIiwiYXVkIjoiNXY2Nmc2b2tl" +
                "OTBlbmJtb2pnNmZzZzI5OTkiLCJldmVudF9pZCI6IjZmMTQyNGFiLWI0MjItNGY4MC04YzMzLTUyMmE3ZGEzOWVkYyIsInRva2VuX3VzZSI6Im" +
                "lkIiwiYXV0aF90aW1lIjoxNjg2NzI0NTg4LCJleHAiOjE2ODY3MjgxODgsImN1c3RvbTpyb2xlIjoidGVuYW50IiwiaWF0IjoxNjg2NzI0NTg4L" +
                "CJlbWFpbCI6ImFudXNoa2Euc2F4ZW5hQHRyZXNhdGEuY29tIn0.ih4Y2b-LufLvvfkmzHCARnI2Cz3BDqT_pQ-i6Kzq9rdG0YQZho7MZGvIM26_0" +
                "jUwlQqws4GV_lSlShvSG6KB6GjjKNFu2h_lGLbBw7PstnBjbqJpEfE8tPB9afm2VsI6FKCB4xckYvAlb3F0joScDWcHWiVgNpneB5XG2OVcUiCogiA" +
                "GpW3v7t6MrENhTop57TASbxfXi8KhAzoeN6s7tCX6_hUHXw-IUePSH9Ib3kvGWRWS9lLO-L8SqP-UIyzP6ABtPN8NNM0oWeyOtXqdwzYFWAqy1SI6k14" +
                "xKPT-g5lvSRdJsZabGu9V51SjVTDfIi9BohJjHWM6D6Zsgdgsow";

        Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer " + actualToken);

        AuthData authData = DuneUtility.getAuthHeaderValue(request);

        assertEquals(expectedToken, authData.getToken());
        assertEquals(1686724588, authData.getAuthTime());
        assertEquals("5v66g6oke90enbmojg6fsg2999", authData.getAud());
        assertEquals("Bearer " + expectedToken, authData.getBearerToken());
        assertEquals("theone", authData.getCustomGroup());
        assertEquals("tenant", authData.getCustomRole());
        assertEquals("anushka.saxena@tresata.com", authData.getEmail());
        assertEquals("6f1424ab-b422-4f80-8c33-522a7da39edc", authData.getEventId());
        assertEquals(1686728188, authData.getExp());
        assertEquals("theone", authData.getGroups().get(0));
        assertEquals(1686724588, authData.getIat());
        assertEquals("https://cognito-idp.us-east-1.amazonaws.com/us-east-1_Q3II4mUkf", authData.getIss());
        assertEquals("id", authData.getTokenUse());
        assertEquals("anushka", authData.getUsername());
        assertEquals(true, authData.getEmailVerified());

    }

    @Test
    public void testGetAuthHeaderValueForInvalidAuth(){
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        String actualToken = "eyJraWQiOiJsZzh1S3QwVkZ2ZVE2aSttYzRrMXo5OEkzeDBtS0dxWU5jZDNJTHUxSXBzPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiI4ZGU3ZDc4Zi1hZDgxLTQyZWEtYjYwMS00YzNiZmM3ZjMzOWQiLCJjb2duaXRvOmdyb3VwcyI6WyJ0aGVvbmUiXSwiZW1haWxfdmVyaWZpZWQiOnRydWUsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy1lYXN0LTEuYW1hem9uYXdzLmNvbVwvdXMtZWFzdC0xX1EzSUk0bVVrZiIsImN1c3RvbTpncm91cCI6InRoZW9uZSIsImNvZ25pdG86dXNlcm5hbWUiOiJhbnVzaGthIiwiYXVkIjoiNXY2Nmc2b2tlOTBlbmJtb2pnNmZzZzI5OTkiLCJldmVudF9pZCI6IjZmMTQyNGFiLWI0MjItNGY4MC04YzMzLTUyMmE3ZGEzOWVkYyIsInRva2VuX3VzZSI6ImlkIiwiYXV0aF90aW1lIjoxNjg2NzI0NTg4LCJleHAiOjE2ODY3MjgxODgsImN1c3RvbTpyb2xlIjoidGVuYW50IiwiaWF0IjoxNjg2NzI0NTg4LCJlbWFpbCI6ImFudXNoa2Euc2F4ZW5hQHRyZXNhdGEuY29tIn0.ih4Y2b-LufLvvfkmzHCARnI2Cz3BDqT_pQ-i6Kzq9rdG0YQZho7MZGvIM26_0jUwlQqws4GV_lSlShvSG6KB6GjjKNFu2h_lGLbBw7PstnBjbqJpEfE8tPB9afm2VsI6FKCB4xckYvAlb3F0joScDWcHWiVgNpneB5XG2OVcUiCogiAGpW3v7t6MrENhTop57TASbxfXi8KhAzoeN6s7tCX6_hUHXw-IUePSH9Ib3kvGWRWS9lLO-L8SqP-UIyzP6ABtPN8NNM0oWeyOtXqdwzYFWAqy1SI6k14xKPT-g5lvSRdJsZabGu9V51SjVTDfIi9BohJjHWM6D6Zsgdgsow";
        Mockito.when(request.getHeader("Authorization")).thenReturn(actualToken);

        assertThrows(RuntimeException.class, () -> DuneUtility.getAuthHeaderValue(request));

    }
}
